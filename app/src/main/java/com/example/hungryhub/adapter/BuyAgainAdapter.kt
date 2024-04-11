package com.example.hungryhub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hungryhub.databinding.ButAgainitemBinding

class BuyAgainAdapter(private val buyAgainFoodName : ArrayList<String>, private val buyAgainFoodPrice : ArrayList<String>, private val buyAgainFoodImage : ArrayList<Int>) :
    RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding = ButAgainitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BuyAgainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        holder.bind(buyAgainFoodName[position],buyAgainFoodPrice[position],buyAgainFoodImage[position])
    }

    override fun getItemCount(): Int = buyAgainFoodName.size

    class BuyAgainViewHolder(private val binding: ButAgainitemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(foodName: String, foodPrice: String, Image: Int) {
            binding.foodName.text = foodName
            binding.foodPrice.text = foodPrice
            binding.foodImage.setImageResource(Image)
        }

    }


}