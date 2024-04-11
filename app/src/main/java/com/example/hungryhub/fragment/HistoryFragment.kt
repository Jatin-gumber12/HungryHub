package com.example.hungryhub.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryhub.R
import com.example.hungryhub.adapter.BuyAgainAdapter
import com.example.hungryhub.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {

    private lateinit var binding : FragmentHistoryBinding
    private lateinit var buyAgainAdapter : BuyAgainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHistoryBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView(){
        val buyAgainFoodName = arrayListOf("Food 1","Food 2","Food 3","Food 4","Food 5")
        val buyAgainFoodPrice = arrayListOf("₹50","₹80","₹70","₹60","₹100")
        val buyAgainFoodImage = arrayListOf(R.drawable.menu1,R.drawable.photo,R.drawable.menu2,R.drawable.menu3,R.drawable.photo3)
        buyAgainAdapter = BuyAgainAdapter(buyAgainFoodName,buyAgainFoodPrice,buyAgainFoodImage)
        binding.buyAgainRecycler.adapter = buyAgainAdapter
        binding.buyAgainRecycler.layoutManager = LinearLayoutManager(requireContext())
    }
    companion object {

    }
}