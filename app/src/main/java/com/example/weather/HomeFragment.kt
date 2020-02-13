package com.example.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.weather.databinding.FragmentHomeBinding
import com.example.weather.di.Injectable
import com.example.weather.di.injectViewModel
import com.example.weather.model.ResultResponse
import com.example.weather.service.Result
import com.example.weather.utility.KelvinToCelsiusConverter
import com.example.weather.viewmodel.MyViewModel
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import javax.inject.Inject


class HomeFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MyViewModel

    private lateinit var navController: NavController

    private lateinit var binding: FragmentHomeBinding

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
        getCurrentWeather()

    }

    private fun getCurrentWeather() {
        viewModel.updatePassword("London")
            .observe(viewLifecycleOwner, Observer { result ->

                when (result) {
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        updateView(result.data)
                        Toast.makeText(context, result.data.main?.temp, Toast.LENGTH_SHORT).show()
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

    fun swipeUp() {
        /*binding.tvLocation.setOnTouchListener(object : OnSwipeTouchListener(
            context as
                    Activity
        ) {
            override fun onSwipeLeft() {
                Toast.makeText(context, "Left", Toast.LENGTH_SHORT).show()
                Log.e("ViewSwipe", "Left")
            }

            override fun onSwipeRight() {
                Toast.makeText(context, "Right", Toast.LENGTH_SHORT).show()
                Log.e("ViewSwipe", "Right")
            }

            override fun onSwipeTop() {
                Toast.makeText(context, "Top", Toast.LENGTH_SHORT).show()
                Log.e("ViewSwipe", "Top")
            }

            override fun onSwipeBottom() {
                Toast.makeText(context, "Bottom", Toast.LENGTH_SHORT).show()
                Log.e("ViewSwipe", "Bottom")
            }
        })*/

    }

    fun updateView(result: ResultResponse) {
        binding.tvLocation.text = "London"
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

}