package com.example.news.ui.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.news.R
import com.example.news.databinding.ActivityMainBinding
import com.example.news.databinding.FragmentBoardBinding
import com.example.news.databinding.FragmentNewsBinding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class BoardFragment : Fragment() {

    private lateinit var binding: FragmentBoardBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BoardAdapter(findNavController())
        binding.viewPager.adapter = adapter
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            activity?.finish()
        }
        binding.skipped.setOnClickListener {
            findNavController().navigateUp()

        }

    }

}