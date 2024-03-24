package com.example.hungryhub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hungryhub.databinding.PopularItemBinding

class PopularAdapter (private val items:List<String>,private val price:List<String>,private val image:List<Int>): RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item = items[position]
        val images = image[position]
        val prices = price[position]
        holder.bind(item,images,prices)
    }
    override fun getItemCount(): Int {
        return items.size
    }
    class PopularViewHolder(private val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imagesView = binding.imageView5
        fun bind(item: String, images: Int, prices: String) {
            binding.FoodNamePopular.text = item
            binding.PricePopular.text = prices
            imagesView.setImageResource(images)
        }

    }

}