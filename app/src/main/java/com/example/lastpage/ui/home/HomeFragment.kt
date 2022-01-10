package com.example.lastpage.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lastpage.R
import com.example.lastpage.database.MainDatabase
import com.example.lastpage.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val application = requireNotNull(activity).application
        val dataSource = MainDatabase.getInstance(application).mainDao

        val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory(dataSource = dataSource) }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.orderList.observe(viewLifecycleOwner, {
            with(_binding!!.ordersRecyclerview) {
                adapter = OrderRecyclerViewAdapter(it) { item ->
                    homeViewModel.completeOrder(item)
                }
            }
        })

        _binding!!.addButton.setOnClickListener {
            findNavController().navigate(R.id.home_to_add_order)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}