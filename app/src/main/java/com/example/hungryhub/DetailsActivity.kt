package com.example.hungryhub

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.hungryhub.model.CartItems
import com.example.hungryhub.databinding.ActivityDetailsActivtityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding :ActivityDetailsActivtityBinding
    private lateinit var mAuth: FirebaseAuth

    private  var foodName :String? = null
    private  var foodImage :String? = null
    private  var foodDescription :String? = null
    private  var foodIngredients :String? = null
    private  var foodPrice :String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsActivtityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        foodName = intent.getStringExtra("MenuItemName")
        foodImage = intent.getStringExtra("MenuItemImage")
        foodDescription = intent.getStringExtra("MenuItemDescription")
        foodIngredients = intent.getStringExtra("MenuItemIngredients")
        foodPrice = intent.getStringExtra("MenuItemPrice")
        binding.detailFoodName.text = foodName
        binding.detailDescription.text = foodDescription
        binding.detailIngredients.text = foodIngredients
        val uriString = foodImage
        val uri = Uri.parse(uriString)
        val detailedFoodImageview = binding.detailFoodImage

        Glide.with(this).load(uri).into(detailedFoodImageview)
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.addToCart.setOnClickListener {
            addItemToCart()
        }

    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = mAuth.currentUser?.uid ?: ""

        val cartItem = CartItems(foodName.toString(),foodPrice.toString(),foodDescription.toString(),foodImage.toString(),1 )

        database.child("Users").child(userId).child("CartItems").push().setValue(cartItem)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "Cart Item saved successfully!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "Failed to save Cart Item", Toast.LENGTH_SHORT).show()
            }
    }


}

