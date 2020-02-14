package com.example.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ItemForecastBinding
import com.example.weather.model.ForecastModel
import com.example.weather.model.ResultResponse

class ForecastAdapter :
    ListAdapter<ForecastModel, ForecastAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemForecastBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val results = getItem(position)
        holder.apply {
            bind(results)
            itemView.tag = results
        }
    }

    inner class ViewHolder(
        private val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ForecastModel) {
            binding.apply {
                forecastModel = item
                executePendingBindings()
            }
        }
    }


}

private class DiffCallback : DiffUtil.ItemCallback<ForecastModel>() {

    override fun areItemsTheSame(oldItem: ForecastModel, newItem: ForecastModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ForecastModel, newItem: ForecastModel): Boolean {
        return oldItem == newItem
    }
}