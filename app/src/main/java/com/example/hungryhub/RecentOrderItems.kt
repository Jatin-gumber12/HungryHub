package com.example.hungryhub

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryhub.adaptar.RecentBuyAdapter
import com.example.hungryhub.databinding.ActivityRecentOrderItemsBinding
import com.example.hungryhub.model.OrderDetails

class RecentOrderItems : AppCompatActivity() {

    private val binding : ActivityRecentOrderItemsBinding by lazy{
        ActivityRecentOrderItemsBinding.inflate(layoutInflater)
    }
    private var allFoodNames: ArrayList<String> = ArrayList()
    private var allFoodImages: ArrayList<String> = ArrayList()
    private var allFoodPrices: ArrayList<String> = ArrayList()
    private var allFoodQuantities: ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val recentOrderItems = intent.getSerializableExtra("RecentBuyOrderItems") as ArrayList<OrderDetails>
        recentOrderItems?.let{orderDetails ->
            if(orderDetails.isNotEmpty()){
                val recentOrderItem = orderDetails[0]

                allFoodNames = recentOrderItem.foodNames as ArrayList<String>
                allFoodImages = recentOrderItem.foodImages as ArrayList<String>
                allFoodPrices = recentOrderItem.foodPrices as ArrayList<String>
                allFoodQuantities = recentOrderItem.foodQuantities as ArrayList<Int>
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
       val rv = binding.recyclerViewBuyItem
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = RecentBuyAdapter(this,allFoodNames,allFoodPrices,allFoodImages,allFoodQuantities)
        rv.adapter = adapter
    }
}