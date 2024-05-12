package com.example.hungryhub.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hungryhub.databinding.MenuItemBinding

class MenuAdapter(
    private val menuItems: ArrayList<String>,
    private val requireContext: ArrayList<String>,
    arrayList: ArrayList<Int>,
    requireContext1: Context
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    private val itemClickListener: OnClickListener?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)

    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(private val binding: MenuItemBinding) :RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailActivity(position)
                }
                // setonclic listner to open details
                val intent = Intent( requireContext, DetailsActivity::class.java)
                intent.putExtra("MenuItemName", menuItemsName.get(position))
                intent.putExtra("MenuItemImage", MenuImage.get(position))
                intent.putExtra("MenuItemDescription", MenuDescriptions.get(position))
                intent.putExtra("MenuItemIngredients", MenuIngredients.get(position))
                intent.putExtra("MenuItemPrice", menuItemPrice.get(position))
                requireContext.startActivity(intent)
            }
        }

        private fun openDetailActivity(position: Int) {

        }

        fun bind(position: Int) {
            binding.apply {
                menuFoodName.text=menuItemsName[position]
                menuPrice.text = menuItemPrice[position]
                val uriString = MenuImage[position]
                val uri = Uri.parse(uriString)
                Glide.with(requireContext).load(uri).into(menuImage)

//               menuImage.setImageResource(MenuImage[position])
            }
        }
    }

    interface OnClickListener{
        fun onItemClick(position: Int)
    }
}

