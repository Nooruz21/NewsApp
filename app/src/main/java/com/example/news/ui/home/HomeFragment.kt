package com.example.news.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.news.R
import com.example.news.databinding.FragmentHomeBinding
import com.example.news.models.News
import java.text.SimpleDateFormat

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

    @SuppressLint("SimpleDateFormat")
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

        val textView: TextView? = activity?.findViewById(R.id.textView)
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val dateString = simpleDateFormat.format(1653799317682)
        textView?.text = String.format("Date: %s", dateString)
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
            dialog.setNegativeButton("Назад") { dialog, _ -> dialog.cancel() }
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