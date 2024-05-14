package com.example.hungryhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryhub.model.OrderDetails
import com.example.hungryhub.adaptar.RecentBuyAdapter
import com.example.hungryhub.databinding.ActivityRecentOrderHistoryBinding
import com.example.hungryhub.databinding.ActivitySignBinding

class RecentOrderHistory : AppCompatActivity() {
    private val binding : ActivityRecentOrderHistoryBinding by lazy {
        ActivityRecentOrderHistoryBinding.inflate(layoutInflater)
    }

    private  lateinit  var allFoodNames : ArrayList<String>
    private  lateinit  var allFoodImages : ArrayList<String>
    private  lateinit  var allFoodPrices : ArrayList<String>
    private  lateinit  var allFoodQuantitys : ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val intent = intent

        val recentorderitemdetails = intent.getParcelableExtra<OrderDetails>("RecentOrderItem")
        if (recentorderitemdetails!=null) {
            allFoodNames = recentorderitemdetails.foodNames as ArrayList<String>
            allFoodImages = recentorderitemdetails.foodImages as ArrayList<String>
            allFoodPrices = recentorderitemdetails.foodPrices as ArrayList<String>
            allFoodQuantitys = recentorderitemdetails.foodQuantities as ArrayList<Int>
        }

        binding.backButton.setOnClickListener {
            finish()
        }
        setAdapter()
    }

    private fun setAdapter() {
        var recview = binding.recentorderrecview
        recview.layoutManager = LinearLayoutManager(this@RecentOrderHistory)
        val adapter = RecentBuyAdapter(this@RecentOrderHistory,allFoodNames,allFoodImages,allFoodPrices,allFoodQuantitys)
        recview.adapter = adapter
    }
}