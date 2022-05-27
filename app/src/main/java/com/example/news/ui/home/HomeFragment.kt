package com.example.news.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.system.Os.rename
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.news.R
import com.example.news.databinding.FragmentHomeBinding
import com.example.news.models.News

class HomeFragment : Fragment() {
    private lateinit var adapter: NewsAdapter
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = NewsAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.newsFragment)
        }

        parentFragmentManager.setFragmentResultListener(
            "rk_news",
            viewLifecycleOwner
        ) { _, bundle ->
            val news = bundle.getSerializable("news") as News
            Log.e("Home", "text=$news")
            adapter.addItem(news)
        }
        binding.recyclerView.adapter = adapter
        rename()
        alert()
    }

    private fun alert() {
        adapter.onLongClick = {

            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Удалить эту новость")
            dialog.setMessage("Вы точно хотите удалить эту новсть?")
            dialog.setPositiveButton("Да") { _, _ ->

                adapter.deleteItemsAndNotifyAdapter(it)
                binding.recyclerView.adapter = adapter
                //Delete items in RecyclerView**

            }
            dialog.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            dialog.show()
        }
    }

    private fun rename() {
        adapter.onClick={
            val bundle=Bundle()
            bundle.putString("key1",it.title)
            findNavController().navigate(R.id.newsFragment,bundle)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}