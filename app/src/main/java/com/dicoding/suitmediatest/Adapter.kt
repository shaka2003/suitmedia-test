package com.dicoding.suitmediatest

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.suitmediatest.api.DataItem
import com.dicoding.suitmediatest.databinding.ItemEachBinding

class UserAdapter(private val activity: Activity) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    private val listUser = ArrayList<DataItem>()

    inner class MyViewHolder(private val binding: ItemEachBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem) {
            Glide.with(binding.ivUser.context)
                .load(item.avatar)
                .into(binding.ivUser)
            val email = item.email
            val firstName = item.firstName
            val lastName = item.lastName

            binding.tvEmail.text = email
            binding.tvUsername.text = "$firstName $lastName"

            itemView.setOnClickListener {
                val intent = Intent()
                intent.putExtra("firstName", firstName)
                intent.putExtra("lastName", lastName)
                activity.setResult(Activity.RESULT_OK, intent)
                activity.finish()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemEachBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    fun setList(newList: List<DataItem>) {
        val diffResult = DiffUtil.calculateDiff(MainDiffCallback(listUser, newList))
        listUser.clear()
        listUser.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class MainDiffCallback(
        private val oldList: List<DataItem>,
        private val newList: List<DataItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
    }
}
