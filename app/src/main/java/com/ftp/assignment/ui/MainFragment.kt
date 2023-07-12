package com.ftp.assignment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftp.assignment.R
import com.ftp.assignment.adapter.RvAdapter
import com.ftp.assignment.databinding.FragmentMainBinding
import com.ftp.assignment.model.DataModel
import com.ftp.assignment.util.NetworkResult
import com.ftp.assignment.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding ?= null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var adapter: RvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RvAdapter()

        adapter.onPostClick = {
            val bundle = Bundle().apply {
                putInt("id", it.id!!)
                putString("title", it.title)
                putString("url", it.url)
            }
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.jsonLiveData.collect { response ->
                when(response) {
                    is NetworkResult.Success -> {
                        val mList = response.data!! as ArrayList<DataModel>
                        fillAdapter(mList)
                    }
                    is NetworkResult.Loading -> {  showLoading()  }
                    is NetworkResult.Error -> {
                        Toast.makeText(requireActivity(), response.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun fillAdapter( list: ArrayList<DataModel>) {
        adapter.addItem(list)
        binding.mainRv.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.mainRv.adapter = adapter
        hideLoading()
    }

    private fun hideLoading() {
        binding.mainProgressBar.visibility = View.GONE
        binding.mainRv.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.mainProgressBar.visibility = View.VISIBLE
        binding.mainRv.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}