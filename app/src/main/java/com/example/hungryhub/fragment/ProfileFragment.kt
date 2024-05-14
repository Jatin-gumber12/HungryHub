package com.example.hungryhub.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hungryhub.R
import com.example.hungryhub.SharedPreference.ProfileSavedPreferences
import com.example.hungryhub.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        setDetailsofProfile()
        binding.apply {
            etname.isEnabled = false
            etemail.isEnabled = false
            etaddress.isEnabled = false
            etphone.isEnabled = false

        binding.editButton.setOnClickListener {

            etname.isEnabled = !etname.isEnabled
            etemail.isEnabled = !etemail.isEnabled
            etaddress.isEnabled = !etaddress.isEnabled
            etphone.isEnabled = !etphone.isEnabled
        }
        }

        binding.saveinformation.setOnClickListener {
            updateDetailsofProfile()
            Toast.makeText(requireContext(), "profile updated succesfully", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun updateDetailsofProfile() {
        val name = binding.etname.text.trim().toString()
        val address = binding.etaddress.text.trim().toString()
        val email = binding.etemail.text.trim().toString()
        val phone = binding.etphone.text.trim().toString()
        ProfileSavedPreferences.setName(requireContext(), name)
        ProfileSavedPreferences.setAddress(requireContext(), address)
        ProfileSavedPreferences.setEmail(requireContext(), email)
        ProfileSavedPreferences.setPhone(requireContext(), phone)
    }

    private fun setDetailsofProfile() {
        val name = ProfileSavedPreferences.getName(requireContext())
        val address = ProfileSavedPreferences.getAddress(requireContext())
        val email = ProfileSavedPreferences.getEmail(requireContext())
        val phone = ProfileSavedPreferences.getPhone(requireContext())
        binding.apply {
            etname.setText(name)
            etemail.setText(email)
            etaddress.setText(address)
            etphone.setText(phone)
        }
    }

    companion object {

    }

}