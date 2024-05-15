package com.example.hungryhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryhub.model.OrderDetails
import com.example.hungryhub.adaptar.RecentBuyAdapter
import com.example.hungryhub.databinding.ActivityRecentOrderHistoryBinding

class RecentOrderHistory : AppCompatActivity() {
    private val binding : ActivityRecentOrderHistoryBinding by lazy {
        ActivityRecentOrderHistoryBinding.inflate(layoutInflater)
    }
    private var allFoodNames: ArrayList<String> = ArrayList()
    private var allFoodImages: ArrayList<String> = ArrayList()
    private var allFoodPrices: ArrayList<String> = ArrayList()
    private var allFoodQuantitys: ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val intent = intent

        val recentorderitemdetails = intent.getSerializableExtra("RecentOrderItem") as? ArrayList<OrderDetails>
        recentorderitemdetails?.let { orderDetails ->
            if (orderDetails.isNotEmpty()) {
                val recentorderitemdetail = orderDetails[0]
                allFoodNames = recentorderitemdetail.foodNames as? ArrayList<String> ?: ArrayList()
                allFoodImages = recentorderitemdetail.foodImages as? ArrayList<String> ?: ArrayList()
                allFoodPrices = recentorderitemdetail.foodPrices as? ArrayList<String> ?: ArrayList()
                allFoodQuantitys = recentorderitemdetail.foodQuantities as? ArrayList<Int> ?: ArrayList()
            }
            Log.d("VALUES","$allFoodNames, $allFoodPrices,$allFoodImages,$allFoodQuantitys")
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