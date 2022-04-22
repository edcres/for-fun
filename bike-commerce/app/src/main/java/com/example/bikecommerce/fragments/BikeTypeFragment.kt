package com.example.bikecommerce.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bikecommerce.OrderViewModel
import com.example.bikecommerce.R
import com.example.bikecommerce.databinding.FragmentBikeTypeBinding

class BikeTypeFragment : Fragment() {

    private var binding: FragmentBikeTypeBinding? = null
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentBikeTypeBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            nextButton.setOnClickListener {
                findNavController().navigate(R.id.action_bikeTypeFragment_to_deliveryFragment)
            }
            cancelButton.setOnClickListener {
                sharedViewModel.resetOrder()
                findNavController().navigate(R.id.action_bikeTypeFragment_to_startFragment)
            }
        }
        // todo: Set the subtotal text
        // todo: Handle checked item
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding= null
    }
}