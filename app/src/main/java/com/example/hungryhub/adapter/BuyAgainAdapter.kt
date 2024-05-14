package com.example.hungryhub.adaptar

import android.content.Context
import android.location.Geocoder.GeocodeListener
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hungryhub.databinding.ButAgainitemBinding
import java.util.ArrayList

class BuyAgainAdapter(private val buyAgainFoodName: MutableList<String>, private val buyAgainFoodPrice: MutableList<String>, private val buyAgainFoodImage: MutableList<String>, private var requireContext : Context) : RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {


    private var buyagainclicklistener : BuyAgainClickListener? = null

    fun setBuyAgainItemClickListener(listener: BuyAgainClickListener)
    {
        buyagainclicklistener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding = ButAgainitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuyAgainViewHolder((binding))
    }

    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        holder.bind(buyAgainFoodName[position], buyAgainFoodPrice[position], buyAgainFoodImage[position])


    }

    override fun getItemCount(): Int = buyAgainFoodName.size

    inner  class BuyAgainViewHolder(private val binding: ButAgainitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(foodName: String, foodPrice: String, foodImage: String) {
            binding.buyAgainFoodName.text = foodName
            binding.buyAgainFoodPrice.text = foodPrice
            val uriString = foodImage
            val uri = Uri.parse(uriString)
            Glide.with(requireContext).load(uri).into(binding.buyAgainFoodImage)
            binding.buyAgainFoodButton.setOnClickListener {
                buyagainclicklistener?.buyAgainButtonClickListener(position)
            }

        }
    }

    interface BuyAgainClickListener
    {
        fun buyAgainButtonClickListener(position: Int)
    }



}