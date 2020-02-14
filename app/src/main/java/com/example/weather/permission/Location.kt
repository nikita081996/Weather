package com.example.weather.permission

import android.Manifest
import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task


/**
 * Location sample.
 *
 * Demonstrates use of the Location API to retrieve the last known location for a device.
 */
abstract class Location : Fragment() {

    private val PERMISSION_ID = 42
    val LOCATION_SETTINGS_REQUEST = 1

    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        getLastLocation()
    }

    private fun getLastLocation() {
        if (checkPermissions()) {
            requestNewLocationData()
        } else {
            requestPermissions()
        }
    }

    abstract fun showMessageWhenLocAndPermDisabled(loc: Boolean)
    abstract fun updateLatLong(lat: String, long: String)

    private fun requestNewLocationData() {
        mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient =
            context?.let { LocationServices.getFusedLocationProviderClient(it) }!!
        locationRequest(mLocationRequest)
    }

    private fun getLatLong(mLocationRequest: LocationRequest) {
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            updateLatLong(
                mLastLocation.latitude.toString(),
                mLastLocation.longitude.toString()
            )
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            } else {
                showMessageWhenLocAndPermDisabled(false)

            }
        }
    }

    private fun locationRequest(mLocationRequest: LocationRequest) {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest)
        val client: SettingsClient? = context?.let { LocationServices.getSettingsClient(it) }
        val task: Task<LocationSettingsResponse> =
            client?.checkLocationSettings(builder.build()) as Task<LocationSettingsResponse>
        task.addOnSuccessListener {
            // All location settings are satisfied. The client can initialize
            getLatLong(mLocationRequest)
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // check the result in onActivityResult().
                    exception.startResolutionForResult(
                        activity as Activity?,
                        LOCATION_SETTINGS_REQUEST
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
    }

    open fun update() {
        getLatLong(mLocationRequest)
    }


}