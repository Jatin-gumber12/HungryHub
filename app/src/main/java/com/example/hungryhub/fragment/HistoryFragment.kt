package com.example.hungryhub.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.hungryhub.CongratsBottomSheet
import com.example.hungryhub.RecentOrderHistory
import com.example.hungryhub.model.OrderDetails
//import com.example.hungryhub.RecentOrderHistory
import com.example.hungryhub.SharedPreference.ProfileSavedPreferences
import com.example.hungryhub.adaptar.BuyAgainAdapter
import com.example.hungryhub.databinding.FragmentHistoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private  var listofbuyAgainFoodNames  : MutableList<MutableList<String>> = mutableListOf(mutableListOf())
    private  var listofbuyAgainFoodPrices  : MutableList<MutableList<String>> = mutableListOf(mutableListOf())
    private  var listofbuyAgainFoodImages  : MutableList<MutableList<String>> = mutableListOf(mutableListOf())
    private  var listofbuyAgainFoodQuantity  : MutableList<MutableList<Int>> = mutableListOf(mutableListOf())
    private lateinit var database: FirebaseDatabase
    private lateinit var userId : String
    private lateinit var mAuth: FirebaseAuth
    private var listoforderitem : MutableList<OrderDetails> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        mAuth = FirebaseAuth.getInstance()
        reteriveBuyHistory()
        binding.recentbuyitem.setOnClickListener {
            sendDataToRecentBuyActivity()
        }
        binding.receivedButton.setOnClickListener {
            updatePaymentOrderStatus()
        }
        return binding.root
    }

    private fun updatePaymentOrderStatus() {
        var itempushkey = listoforderitem[0].itemPushKey
        var completeorderref = database.reference.child("CompletedOrderDetails").child(itempushkey!!)
        completeorderref.child("paymentReceived").setValue(true)
        binding.recentorderstatus.background.setTint(Color.GREEN)
    }




    private  fun  reteriveBuyHistory()
    {
        binding.recentbuyitem.visibility = View.INVISIBLE
        userId = mAuth.currentUser?.uid ?: ""
        database = FirebaseDatabase.getInstance()
        var buyitemref: DatabaseReference = database.reference.child("Users").child(userId).child("BuyHistory")
        var sortingquery = buyitemref.orderByChild("currentTime")


        sortingquery.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (buySnapshot in dataSnapshot.children) {

                    val buyhistoryitem = buySnapshot.getValue(OrderDetails::class.java)

                    buyhistoryitem?.let {
                        listoforderitem.add(it)
                    }
                }
                listoforderitem.reverse()
//                listoforderitem.sortByDescending { it.timestamp ?: 0L }
                for (item in listoforderitem) {
                    item.foodNames!!.forEach { foodnames ->

                        Log.d("firstiem",listoforderitem[0].foodNames?.get(0) ?: "")
                        Log.d("firstisfdem",listoforderitem[0].foodImages?.get(0) ?: "")
                    }

//                  item.foodimages!!.forEach { foodimage ->
//                      Log.d("foodimage", foodimage)
//                  }
//                  item.foodprices!!.forEach { foodprice ->
//                      Log.d("foodprices", foodprice)
//                  }
//
//                   var username = item.username!!
//                  Log.d("foodnames", username)



                }
                if (listoforderitem.isNotEmpty()) {
                    setDataInRecentBuyItem()
                    setPreviousBuyRecView()
                }
            }


            override fun onCancelled(databaseError: DatabaseError) {

                println("Error: ${databaseError.message}")
            }
        })

//        buyAgainAdapter.notifyDataSetChanged()
    }

////    private fun setDataInRecentBuy()
//    {
////        if (listofbuyAgainFoodNames.isNotEmpty() && listofbuyAgainFoodImages.isNotEmpty() && listofbuyAgainFoodPrices.isNotEmpty()) {
////
////            val firstInnerListofname = listofbuyAgainFoodNames[0]
////            val firstInnerListofprice = listofbuyAgainFoodPrices[0]
////            val firstInnerListofimage = listofbuyAgainFoodImages[0]
////
////             Log.d("first", firstInnerListofname.size.toString())
////            if (firstInnerListofname.isNotEmpty() && firstInnerListofimage.isNotEmpty() && firstInnerListofprice.isNotEmpty()) {
////
////                Toast.makeText(requireContext(), "firstiner", Toast.LENGTH_SHORT).show()
////                val firstFoodName = firstInnerListofname[0]
////                val firstFoodimage = firstInnerListofimage[0]
////                val firstFoodprice = firstInnerListofprice[0]
////
////            }
////        }
//
//        var firstFoodName: String? = null
//        var firstFoodprice : String ? = null
//        var firstFoodimage : String ? = null
//        for (outerList in listofbuyAgainFoodNames) {
//            for (foodName in outerList) {
//                firstFoodName = foodName
//                break  // Exit the inner loop after the first item
//            }
//
//            if (firstFoodName != null) {
//                break  // Exit the outer loop after assigning the first item
//            }
//        }
//        for (outerList in listofbuyAgainFoodImages) {
//            for (foodimages in outerList) {
//                firstFoodimage = foodimages
//                break  // Exit the inner loop after the first item
//            }
//
//            if (firstFoodimage != null) {
//                break  // Exit the outer loop after assigning the first item
//            }
//        }
//        for (outerList in listofbuyAgainFoodPrices) {
//            for (foodprices in outerList) {
//                firstFoodprice = foodprices
//                break  // Exit the inner loop after the first item
//            }
//
//            if (firstFoodprice != null) {
//                break  // Exit the outer loop after assigning the first item
//            }
//        }
//        Log.d("first",firstFoodName.toString() + firstFoodimage.toString() + firstFoodprice.toString())
//           if (!(firstFoodimage.isNullOrEmpty() || firstFoodName.isNullOrEmpty()))
//               {
//                   binding.recentbuyfoodname.text = firstFoodName
//                   binding.recentfoodprice.text = firstFoodprice
//                   val uriString = firstFoodimage
//                   val uri = Uri.parse(uriString)
//                   Glide.with(requireContext()).load(uri).into(binding.recentbuyfoodimage)
//
//                   binding.recentbuyitem.setOnClickListener {
//                      showRecentBuyDetails()
//                   }
//               }
//    }


    private fun setDataInRecentBuyItem()
    {
        binding.recentbuyitem.visibility = View.VISIBLE
       val recentOrderItem = listoforderitem.firstOrNull()
       recentOrderItem?.let {
           with(binding) {
               recentbuyfoodname.text = it.foodNames?.firstOrNull()?: ""
               recentfoodprice.text= it.foodPrices?.firstOrNull()?: ""
               val image = it.foodImages?.firstOrNull()?: ""
               val uri = Uri.parse(image)
               Glide.with(requireContext()).load(uri).into(recentbuyfoodimage)

               val isOrderAccepted = true
         Log.d("Order Accepted","setDataInRecentBuyItem : $isOrderAccepted $listoforderitem[0]")
           if(isOrderAccepted){

               receivedButton.visibility = View.VISIBLE

           }}

        }

    }

    private fun setPreviousBuyRecView()
    {
        var listoffirstfoodnames = mutableListOf<String>()
        var listoffirstfoodimages = mutableListOf<String>()
        var listoffirstfoodprices = mutableListOf<String>()
        for (i in (1..listoforderitem.size-1))
        {
            listoffirstfoodnames.add(listoforderitem[i].foodNames?.get(0) ?: "")
            listoffirstfoodimages.add(listoforderitem[i].foodImages?.get(0) ?: "")
            listoffirstfoodprices.add(listoforderitem[i].foodPrices?.get(0) ?: "")
        }
        val rv = binding.ButAgainRecyclerView
        rv.layoutManager = LinearLayoutManager(requireContext())
        buyAgainAdapter = BuyAgainAdapter(listoffirstfoodnames,listoffirstfoodprices,listoffirstfoodimages,requireContext())
        rv.adapter = buyAgainAdapter
        buyAgainAdapter.setBuyAgainItemClickListener(object : BuyAgainAdapter.BuyAgainClickListener
        {
            override fun buyAgainButtonClickListener(position: Int) {
                buyAgainThisItem(position)
            }

            })

    }

    private fun buyAgainThisItem(positon : Int)
    {
        var buyAgainItemNewPushKey =  database.reference.child("OrderDetails").push().key

        var currentime = System.currentTimeMillis()
        var buyAgainOrderDetails = OrderDetails(userId,listoforderitem[positon+1].userName,listoforderitem[positon+1].foodNames,
            listoforderitem[positon+1].foodPrices, listoforderitem[positon+1].foodImages,listoforderitem[positon+1].foodQuantities,
            listoforderitem[positon+1].address,   listoforderitem[positon+1].totalPrice,   listoforderitem[positon+1].phoneNumber,   currentime,
            buyAgainItemNewPushKey,false,false)

        var buyagainref = database.reference.child("OrderDetails").child(buyAgainItemNewPushKey!!).setValue(buyAgainOrderDetails).addOnSuccessListener {
            val bottomSheetDeialog = CongratsBottomSheet()
            bottomSheetDeialog.show(childFragmentManager,"Test")
            addThisItemToBuyHistory(buyAgainOrderDetails)
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Order  Failed Please try again", Toast.LENGTH_SHORT).show()
        }

    }

    @SuppressLint("SuspiciousIndentation")
    private fun addThisItemToBuyHistory(buyAgainOrderDetails: OrderDetails) {
        var buhistoryref = database.reference.child("Users").child(userId).child("BuyHistory").child(buyAgainOrderDetails.itemPushKey!!)
        buhistoryref.setValue(buyAgainOrderDetails).addOnSuccessListener {
            Log.d("buyhistory","item added to buy history")
            ProfileSavedPreferences.setItemPushKey(requireContext(), buyAgainOrderDetails.itemPushKey!!)
        }.addOnFailureListener{
            Log.d("buyhistory","failed to add item to buy history")
        }

    }

    private fun sendDataToRecentBuyActivity()
    {
//        var recentbuyorderdetails = listoforderitem[0]
//        var intent = Intent(requireContext(),RecentOrderHistory::class.java)
//        intent.putExtra("RecentOrderItem",recentbuyorderdetails)
//        startActivity(intent)

        listoforderitem.firstOrNull()?.let { recentBuy ->

            val intent = Intent(requireContext(), RecentOrderHistory::class.java)
            intent.putExtra("RecentBuyOrderItem", recentBuy)
            // Pass recentBuy instead of listOfOrderItem
            startActivity(intent)
        }
    }

}