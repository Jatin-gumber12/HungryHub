package com.example.hungryhub.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hungryhub.DetailsActivity
import com.example.hungryhub.databinding.MenuItemBinding
import com.example.hungryhub.model.MenuItem

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val requireContext: Context
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private val itemClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailsActivity(position)
                }

                //setOnclick


            }
        }

        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                MenuFoodName.text = menuItem.foodName
                MenuPrice.text = menuItem.foodPrice
                val uri = Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(uri).into(MenuImage)


            }
        }

    }

    private fun openDetailsActivity(position: Int) {
        val menuItem = menuItems[position]
        // a intent to open detail Activity
        val intent = Intent(requireContext,DetailsActivity::class.java).apply {
            putExtra("MenuItemName",menuItem.foodName)
            putExtra("MenuItemImage",menuItem.foodImage)
            putExtra("MenuItemDescription",menuItem.foodDesc)
            putExtra("MenuItemIngredients",menuItem.foodIngredients)
            putExtra("MenuItemPrice",menuItem.foodPrice)
        }
        //start the details activity
        requireContext.startActivity(intent)

    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

}




