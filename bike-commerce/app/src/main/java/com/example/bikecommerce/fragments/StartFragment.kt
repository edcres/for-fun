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
import com.example.bikecommerce.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private var binding: FragmentStartBinding? = null
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            beginOrderBtn.setOnClickListener {
                sharedViewModel.setBikeType(OrderViewModel.MOUNTAIN_BIKE)
                findNavController().navigate(R.id.action_startFragment_to_bikeTypeFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding= null
    }
}