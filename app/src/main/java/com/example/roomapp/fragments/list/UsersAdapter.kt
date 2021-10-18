package com.example.roomapp.fragments.list

import android.annotation.SuppressLint
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.User

class UsersAdapter(): RecyclerView.Adapter<UsersAdapter.ViewHolder>(){

    private var usersList = emptyList<User>()

    class ViewHolder(val item: View): RecyclerView.ViewHolder(item) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = usersList[position]
        holder.item.findViewById<TextView>(R.id.tv_id).text = currentItem.id.toString()
        holder.item.findViewById<TextView>(R.id.tv_first_name).text = currentItem.firstName
        holder.item.findViewById<TextView>(R.id.tv_last_name).text = currentItem.lastName
        holder.item.findViewById<TextView>(R.id.tv_age).text = currentItem.age.toString()
        holder.item.findViewById<ConstraintLayout>(R.id.user_item_layout).setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.item.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(users:List<User>) {
        this.usersList = users
        notifyDataSetChanged()
    }
}