package com.example.weather

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.weather.databinding.FragmentHomeBinding
import com.example.weather.di.Injectable
import com.example.weather.di.injectViewModel
import com.example.weather.model.ResultResponse
import com.example.weather.permission.Location
import com.example.weather.service.Result
import com.example.weather.utility.AppUtils
import com.example.weather.utility.KelvinToCelsiusConverter
import com.example.weather.viewmodel.MyViewModel
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import java.io.IOException
import java.util.*
import javax.inject.Inject


class HomeFragment : Location(), Injectable, View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.forecast_btn -> {
                val action = HomeFragmentDirections.homeToForecastWeatherNavGraph(city)
                navController.navigate(action)
            }
        }
    }

    override fun showMessageWhenLocAndPermDisabled(loc: Boolean) {
        if (loc)
            context?.toast("Please turn on location")
        else
            context?.toast("Please grant permission for location")
        getCurrentWeather("London")
    }

    override fun updateLatLong(lat: String, long: String) {
        val geoCoder = Geocoder(context, Locale.getDefault()) //it is Geocoder
        try {
            val address = geoCoder.getFromLocation(lat.toDouble(), long.toDouble(), 1)
            getCurrentWeather(address[0]?.locality ?: "London")
        } catch (e: IOException) {
            context?.toast("Unable to get current location")
            getCurrentWeather("London")
        } catch (e: NullPointerException) {
            context?.toast("Unable to get current location")
            getCurrentWeather("London")
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MyViewModel

    private lateinit var navController: NavController

    private lateinit var binding: FragmentHomeBinding
    private lateinit var city: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.progressBar.visibility = View.VISIBLE
        binding.forecastBtn.setOnClickListener(this)
        setUpSearchView()
    }

    private fun setUpSearchView() {
        binding.searchCity.setQuery("", false)
        binding.searchCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                view?.let { AppUtils.hideKeyboard(it) }
                getCurrentWeather(query)
                return true
            }
        })
    }
    private fun getCurrentWeather(city: String) {
        this.city = city
        viewModel.todayWeather(city)
            .observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        updateView(result.data)
                    }
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        context!!.alert(
                            result.message,
                            getString(R.string.app_name)
                        ) { yesButton { } }
                            .show()
                    }
                }
            })
    }

    fun updateView(result: ResultResponse) {
        binding.tvWeather.visibility = View.VISIBLE
        binding.tvLocation.text = result.name
        binding.tvWeather.text =
            result.main?.temp?.toDouble()?.let { KelvinToCelsiusConverter.convertKelToCel(it) }

        binding.tvWeatherDesc.text = result.weather?.get(0)?.main
        binding.tvWeatherMaxMin.text =
            result.main?.temp_max?.toDouble()?.let { KelvinToCelsiusConverter.convertKelToCel(it) }
                .plus("/")
                .plus(result.main?.temp_min?.toDouble()?.let {
                    KelvinToCelsiusConverter.convertKelToCel(
                        it
                    )
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (LOCATION_SETTINGS_REQUEST == 1 && resultCode == 0) {
            showMessageWhenLocAndPermDisabled(true)
        } else if (LOCATION_SETTINGS_REQUEST == 1 && resultCode == -1) {
            update()
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView? = searchItem?.actionView as SearchView

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        return super.onCreateOptionsMenu(menu)
    }
*/
}