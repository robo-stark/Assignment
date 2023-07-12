package com.ftp.assignment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ftp.assignment.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id", 0)
        val title = arguments?.getString("title", "No Title")
        val url = arguments?.getString("url", "")

        binding.apply {
            detailTitle.text = title
            detailId.text = id.toString()
            Picasso.get().load(url).into(detailImage)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}