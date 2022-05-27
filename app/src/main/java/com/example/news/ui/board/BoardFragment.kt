package com.example.news.ui.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.news.R
import com.example.news.databinding.ActivityMainBinding
import com.example.news.databinding.FragmentBoardBinding
import com.example.news.databinding.FragmentNewsBinding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import me.relex.circleindicator.CircleIndicator
import me.relex.circleindicator.CircleIndicator3

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
            //dsad
        }
        binding.skipped.setOnClickListener {
            findNavController().navigateUp()

        }
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val indicator : CircleIndicator3? = activity?.findViewById<CircleIndicator3>(R.id.circle)
        indicator?.setViewPager(binding.viewPager)

    }

}