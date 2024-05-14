package com.example.hungryhub.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.hungryhub.MenuBottomSheetFragment
import com.example.hungryhub.model.MenuItem
import com.example.hungryhub.R
import com.example.hungryhub.adaptar.PopularAdapter
import com.example.hungryhub.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private lateinit var database: FirebaseDatabase
    private lateinit var foodNames : MutableList<String>
    private lateinit var foodPrices: MutableList<String>
    private lateinit var imageuris: MutableList<String>
    private lateinit var ingredients: MutableList<String>
    private lateinit var foodDescriptions: MutableList<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewAllMenu.setOnClickListener {
            val bottomSheetDialog = MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"Test")
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner22, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner33, ScaleTypes.FIT))
        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)

        retrieveMenuItems()
//        val foodName = listOf("Burger", "sandwich", "momo", "item")
//        val Price = listOf("$5", "$7", "$8", "$10")
//        val populerFoodImages =
//            listOf(R.drawable.menu1, R.drawable.menu2, R.drawable.menu3, R.drawable.menu4)
//val adapter = PopularAdapter(foodName,Price,populerFoodImages,requireContext())
//        binding.PopulerRecyclerView.layoutManager =LinearLayoutManager(requireContext())
//        binding.PopulerRecyclerView.adapter =adapter
    }
    companion object {
    }
    private fun  retrieveMenuItems()
    {
        // Get reference to the Firebase database
        database = FirebaseDatabase.getInstance()

        val foodRef: DatabaseReference = database.reference.child("Vendor").child("Menu")
//        Toast.makeText(this@AllItemActivity, "${foodRef.toString()}", Toast.LENGTH_SHORT).show()

        // Array to store the details  of all menu items
        foodNames = mutableListOf()
        foodPrices= mutableListOf()
        foodDescriptions = mutableListOf()
        imageuris= mutableListOf()
        ingredients = mutableListOf()
        // Fetch data from the database
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Loop through each food item
                for (foodSnapshot in dataSnapshot.children) {
                    // Get the FoodItem object from the child node
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)

                    // Add the foodname to the foodNames list
                    menuItem?.foodName?.let {
                        foodNames.add(it)
                    }
                    menuItem?.foodPrice?.let {
                        foodPrices.add(it)
                    }
                    menuItem?.foodImage?.let {
                        imageuris.add(it)
                    }
                    menuItem?.foodIngredient?.let {
                        ingredients.add(it)
                    }
                    menuItem?.foodDescription?.let {
                        foodDescriptions.add(it)
                    }
                }

                settingMenuItemInRandomOrder()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here if any
                println("Error: ${databaseError.message}")
            }
        })
    }

    private fun settingMenuItemInRandomOrder() {
        val indices = foodNames.indices.toList().shuffled()

        // Use the shuffled indices to shuffle all the lists
        val shuffledFoodNames = indices.map { foodNames[it] }
        val shuffledFoodPrices = indices.map { foodPrices[it] }
        val shuffledImageUris = indices.map { imageuris[it] }
        val shuffledIngredients = indices.map { ingredients[it] }
        val shuffledFoodDescriptions = indices.map { foodDescriptions[it] }

        // Select a subset of items (e.g., 6 items)
        val numItemsToShow = 6
        val subsetFoodNames = shuffledFoodNames.take(numItemsToShow)
        val subsetFoodPrices = shuffledFoodPrices.take(numItemsToShow)
        val subsetImageUris = shuffledImageUris.take(numItemsToShow)
        val subsetIngredients = shuffledIngredients.take(numItemsToShow)
        val subsetFoodDescriptions = shuffledFoodDescriptions.take(numItemsToShow)
        setAdapter(subsetFoodNames, subsetFoodPrices, subsetImageUris, subsetFoodDescriptions, subsetIngredients)
    }


    private  fun setAdapter(subsetFoodNames : List<String>, subsetFoodPrices :  List<String>, subsetImageUris: List<String>, subsetFoodDescriptions: List<String>, subsetIngredients: List<String>)
    {
        val adapter = PopularAdapter(
            subsetFoodNames, subsetFoodPrices, subsetImageUris, subsetFoodDescriptions, subsetIngredients,
            requireContext())

        binding.PopulerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.PopulerRecyclerView.adapter = adapter
    }
}

/*
Shuffling the Order of Menu Items Randomly
In the provided code, the function settingMenuItemInRandomOrder() is responsible for shuffling the order of menu items randomly. This is achieved using the following steps:

Create a list of indices representing the positions of menu items in the original lists (foodNames, foodPrices, imageuris, ingredients, foodDescriptions).

Shuffle the list of indices using the shuffled() function. This ensures that the order of indices is random.

Map the shuffled indices back to the original lists to create new lists with the menu items in a random order.
 */
/*

Certainly! Here's an explanation of the retreriveMenuItems() function in a similar style as before:

Retrieving Menu Items from Firebase Realtime Database
The retreriveMenuItems() function is responsible for fetching menu items from a Firebase Realtime Database and organizing them for display in the app. This process involves the following steps:

Database Initialization: The function initializes a connection to the Firebase Realtime Database using the FirebaseDatabase.getInstance() method.

Database Reference Creation: A reference to a specific location in the database where menu items are stored is created using the reference.child() method. This reference points to the "Vendor/Menu" location in the database.

Data Retrieval: The function attaches a single-value event listener (addListenerForSingleValueEvent()) to the database reference. This listener is triggered once and fetches the data from the specified location.

Data Processing: Inside the onDataChange callback of the listener, the function iterates through each child node under the specified reference. For each menu item, it retrieves the data using the getValue(MenuItem::class.java) method, where MenuItem is a data class representing the structure of a menu item.

Data Extraction: The function extracts relevant attributes from the retrieved menu item, such as the food name, price, image URL, ingredients, and description. These attributes are added to separate lists (foodNames, foodPrices, imageuris, ingredients, foodDescriptions) to organize the data

 */