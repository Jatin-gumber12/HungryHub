package com.example.hungryhub.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hungryhub.adapter.MenuAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryhub.R
import com.example.hungryhub.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentMenuBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuBottomSheetBinding.inflate(inflater,container,false)

        binding.backButton.setOnClickListener {
            dismiss()
        }

        val menuFoodName = listOf("Burger","Sandwich","Momo","Item","Sandwich","Momo")
        val menuItemPrice = listOf("₹60","₹80","₹100","90","₹80","₹50")
        val menuImage = listOf(
            R.drawable.menu2,
            R.drawable.menu1,
            R.drawable.menu3 ,
            R.drawable.menu4,
            R.drawable.photo2,
            R.drawable.photo3
        )

        val adapter = MenuAdapter (ArrayList(menuFoodName),ArrayList(menuItemPrice),ArrayList(menuImage))
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {

    }
}