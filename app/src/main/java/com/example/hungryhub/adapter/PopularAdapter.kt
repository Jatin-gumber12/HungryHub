
package com.example.hungryhub.adaptar

import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hungryhub.DetailsActivity
import com.example.hungryhub.databinding.PopularItemBinding

class PopularAdapter(private val items:List<String>, private val price:List<String>, private val image: List<String>,private val Descriptions:List<String>,private val Ingredients:List<String>, private val requireContext:Context) : RecyclerView.Adapter<PopularAdapter.PouplerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PouplerViewHolder {
        return PouplerViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: PouplerViewHolder, position: Int) {
        val item = items[position]
        val images = image[position]
        val price = price[position]
        val fooddescription = Descriptions[position]
        val foodingedient  = Ingredients[position]
        holder.bind(item,price,images)
        holder.itemView.setOnClickListener {
            val intent = Intent( requireContext, DetailsActivity::class.java)
            intent.putExtra("MenuItemName", item)
            intent.putExtra("MenuItemImage", images)
            intent.putExtra("MenuItemPrice", price)
            intent.putExtra("MenuItemDescription", fooddescription)
            intent.putExtra("MenuItemIngredients", foodingedient)
            requireContext.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
    inner class PouplerViewHolder (private val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imagesView = binding.imageView5
        fun bind(item: String,price: String, images: String) {
            binding.FoodNamePopular.text = item
            binding.PricePopular.text = price
            val uriString = images
            val uri = Uri.parse(uriString)
            Glide.with(requireContext).load(uri).into(imagesView)
        }
    }

}