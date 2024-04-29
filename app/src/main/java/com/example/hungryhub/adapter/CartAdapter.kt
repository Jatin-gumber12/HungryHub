package com.example.hungryhub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hungryhub.databinding.CartItemBinding

class CartAdapter(private val CartItems : MutableList<String>,private val CartItemPrice : MutableList<String>,private var cartImage : MutableList<Int>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {


    private val itemQuantities = MutableList(CartItems.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false )
        return CartViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = CartItems.size

    inner class CartViewHolder(private val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                CartFoodName.text = CartItems[position]
                Cartfoodprice.text = CartItemPrice[position]
                CartImage.setImageResource(cartImage[position])
                cartItemQuantity.text = quantity.toString()


                minusbutton.setOnClickListener{
                    decreaseQuantity(position)
                }

                plusbutton.setOnClickListener {
                    increaseQuantity(position)
                }

                deletebutton.setOnClickListener{
                    val itemPosition = adapterPosition
                    if(itemPosition != RecyclerView.NO_POSITION){
                        deleteItem(itemPosition)
                    }
                }


            }
        }
        private fun decreaseQuantity(position: Int){
            if (itemQuantities[position]>=1){
                itemQuantities[position]--
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
            if(itemQuantities[position] == 0){
                deleteItem(position)
            }

        }
        private fun increaseQuantity(position: Int){
            if (itemQuantities[position]<20){
                itemQuantities[position]++
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun deleteItem(position: Int){
            CartItems.removeAt(position)
            cartImage.removeAt(position)
            CartItemPrice.removeAt(position)
            itemQuantities.removeAt(position) // Remove the corresponding quantity
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, CartItems.size)
        }



    }
}