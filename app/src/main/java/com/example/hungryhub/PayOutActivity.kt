package com.example.hungryhub

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.hungryhub.model.OrderDetails
import com.example.hungryhub.SharedPreference.ProfileSavedPreferences
import com.example.hungryhub.databinding.ActivityPayOutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.function.LongFunction

class PayOutActivity : AppCompatActivity() {
    lateinit var binding : ActivityPayOutBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var name : String
    private lateinit var address: String
    private lateinit var phone : String
    private lateinit var totalamount : String
    private lateinit var orderFoodItemName: ArrayList<String>
    private lateinit var orderItemsFoodPrice: ArrayList<String>
    private lateinit var orderItemsFoodImage: ArrayList<String>
    private lateinit var orderItemsFoodDescription: ArrayList<String>
    private lateinit var orderItemsFoodQuantity: ArrayList<Int>
    private lateinit var database: DatabaseReference
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUserDetails()
        auth = FirebaseAuth.getInstance()
        val intent = intent
        orderFoodItemName = intent.getStringArrayListExtra("OrdersFoodItemName") as ArrayList<String>
        orderItemsFoodPrice = intent.getStringArrayListExtra("OrdersFoodItemPrice") as ArrayList<String>
        orderItemsFoodImage = intent.getStringArrayListExtra("OrdersFoodItemImage") as ArrayList<String>
        orderItemsFoodDescription = intent.getStringArrayListExtra("OrdersFoodItemDescription") as ArrayList<String>
        orderItemsFoodQuantity = intent.getIntegerArrayListExtra("OrdersFoodItemQuantity") as ArrayList<Int>



        totalamount = totalAmount()+ "₹"
        binding.totalamount.isEnabled = false
        binding.totalamount.setText(totalamount)
        binding.PlaceMyOrder.setOnClickListener {

            name = binding.etname.text.toString().trim()
            address = binding.etaddress.text.toString().trim()
            phone = binding.etphone.text.toString().trim()
            if(isAllDataEntered())
            {
                placeOrder()
            }
            else
            {
                Toast.makeText(this@PayOutActivity, "Please enter all the details", Toast.LENGTH_SHORT).show()

            }



        }
    }

    private fun placeOrder() {
        database = FirebaseDatabase.getInstance().getReference()
        userId = auth.currentUser?.uid ?: ""
        var time = System.currentTimeMillis()
        var itempushkey = database.child("OrderDetails").push().key
        val orderDetails  = OrderDetails(userId,name,orderFoodItemName,orderItemsFoodPrice,orderItemsFoodImage, orderItemsFoodQuantity,address,totalamount,phone,time,itempushkey,false,false)
        val orderRef = database.child("OrderDetails").child(itempushkey!!)
        orderRef.setValue(orderDetails).addOnSuccessListener {
            val bottomSheetDialog = CongratsBottomSheet()
            bottomSheetDialog.show(supportFragmentManager,"Test")
            removeThisItemFromCarts()
            addOrderToHistory(orderDetails)
        }.addOnFailureListener {
            Toast.makeText(applicationContext, "Order Sent Failed Please try again", Toast.LENGTH_SHORT).show()
        }

    }

    @SuppressLint("SuspiciousIndentation")
    private fun removeThisItemFromCarts() {
        val cartitemreference = database.child("Users").child(userId).child("CartItems")
        cartitemreference.removeValue()
    }

    private fun isAllDataEntered(): Boolean {

        Toast.makeText(this, "$name + $address + $phone", Toast.LENGTH_SHORT).show()

        return !(name.isEmpty() || address.isEmpty() || phone.isEmpty())

    }

    private fun totalAmount():String
    {
        var totalamount = 0
        for(i in 0 until orderItemsFoodPrice.size)
        {
            var price = orderItemsFoodPrice[i]
            val lastchar = price.last()
            val priceintvalue  =   if (lastchar == '$')
            {
                price.dropLast(1).toInt()
            }
            else
            {
                price.toInt()
            }
            Log.d("result",priceintvalue.toString())
            var quantity = orderItemsFoodQuantity[i]
            totalamount  += priceintvalue * quantity
        }
        return  totalamount.toString()
    }
    private fun addOrderToHistory(orderDetails: OrderDetails)
    {

        database.child("Users").child(userId).child("BuyHistory").child(orderDetails.itemPushKey!!).setValue(orderDetails).addOnSuccessListener {
            Log.d("buyhistory","item added to buy history")
            ProfileSavedPreferences.setItemPushKey(this@PayOutActivity, orderDetails.itemPushKey!!)  // it is for notificatio feature
        }.addOnFailureListener {
            Log.d("buyhistory","failed to add item to buy history")
        }
    }
    private fun setUserDetails()
    {
        val name = ProfileSavedPreferences.getName(this@PayOutActivity)
        val address = ProfileSavedPreferences.getAddress(this@PayOutActivity)
        val phone = ProfileSavedPreferences.getPhone(this@PayOutActivity)
        binding.apply {
            etname.setText(name)
            etaddress.setText(address)
            etphone.setText(phone)
        }
    }


}