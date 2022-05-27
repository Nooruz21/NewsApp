package com.example.news.ui.board


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.databinding.ItemBoardBinding


class BoardAdapter(val  navController: NavController) :
    RecyclerView.Adapter<BoardAdapter.ViewHolder>() {


private val image= arrayListOf(R.drawable.img,R.drawable.img_1,R.drawable.img_2)
    private val list = arrayListOf("Hello","Привет","Салам")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBoardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BoardAdapter.ViewHolder, position: Int) {
       holder.bind(position)
    }


    override fun getItemCount() =3




    inner class ViewHolder(private var binding: ItemBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.textTittle.text=list[position]
            binding.imageView.setImageResource(image[position])
            if (position==list.lastIndex){
                binding.btnStart.visibility==View.VISIBLE
            }else{
                binding.btnStart.visibility==View.INVISIBLE

            }
            binding.btnStart.setOnClickListener{
                navController.navigateUp()
            }
        }




    }


}