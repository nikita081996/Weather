package com.example.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.adapter.ForecastAdapter
import com.example.weather.databinding.FragmentForecastWeatherBinding
import com.example.weather.databinding.FragmentHomeBinding
import com.example.weather.di.Injectable
import com.example.weather.di.injectViewModel
import com.example.weather.model.ForecastModel
import com.example.weather.model.ForecastResponse
import com.example.weather.model.ResultResponse
import com.example.weather.service.Result
import com.example.weather.utility.CalendarHelper
import com.example.weather.utility.KelvinToCelsiusConverter
import com.example.weather.viewmodel.MyViewModel
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import javax.inject.Inject


class ForecastWeatherFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MyViewModel

    private lateinit var navController: NavController

    private lateinit var binding: FragmentForecastWeatherBinding
    private var city: String = "London"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        binding = FragmentForecastWeatherBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.progressBar.visibility = View.VISIBLE
        if (arguments != null) {
            val args = ForecastWeatherFragmentArgs.fromBundle(arguments!!)
            city = args.city
        }
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        var adapter = ForecastAdapter()
        var linearLayoutManager = LinearLayoutManager(
            context, // Context
            RecyclerView.VERTICAL,
            false
        )
        binding.forecastRecyclerView.layoutManager =
            linearLayoutManager
        binding.forecastRecyclerView.adapter = adapter
        getForecastWeather(city, adapter)
    }

    private fun getForecastWeather(city: String, adapter: ForecastAdapter) {
        viewModel.forecastWeather(city)
            .observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        updateView(result.data, adapter)
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

    fun updateView(result: ForecastResponse, adapter: ForecastAdapter) {
        var listForecastModel: ArrayList<ForecastModel> = ArrayList()
        for(i in 0..result.list?.size!! -1) {
            var forecast = ForecastModel()
            forecast.dt_txt = CalendarHelper.getCurrentDateTime(result.list[i]?.dt_txt)
            forecast.temp_max_min = result.list[i]?.main?.temp_max?.toDouble()?.let { KelvinToCelsiusConverter.convertKelToCel(it) }
                .plus("/")
                .plus(result.list[i]?.main?.temp_min?.toDouble()?.let {
                    KelvinToCelsiusConverter.convertKelToCel(
                        it
                    )
                })
            forecast.temp = result.list[i]?.main?.temp?.toDouble()?.let { KelvinToCelsiusConverter.convertKelToCel(it) }
            listForecastModel.add(forecast)
        }
        adapter?.submitList(listForecastModel)
        adapter?.notifyDataSetChanged()
    }
}