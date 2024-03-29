package com.example.hungryhub.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryhub.R
import com.example.hungryhub.adapter.CartAdapter
import com.example.hungryhub.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)

        val cartFoodName = listOf("Burger","Sandwich","Momo","Item","Sandwich","Momo")
        val cartItemPrice = listOf("₹60","₹80","₹100","90","₹80","₹50")
        val cartImage = listOf(
            R.drawable.menu2,
            R.drawable.menu1,
            R.drawable.menu3 ,
            R.drawable.menu4,
            R.drawable.photo2,
            R.drawable.photo3
        )

        val adapter = CartAdapter(ArrayList(cartFoodName),ArrayList(cartItemPrice),ArrayList(cartImage))
        binding.CartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.CartRecyclerView.adapter = adapter

        return binding.root
    }

    companion object {

    }
}