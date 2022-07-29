package com.example.personal.ui.friendList.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.common.base.utils.MyApplication
import com.example.personal.R
import com.example.personal.bean.Friend

class FriendListAdapter(var friendList : List<Friend>) :
    RecyclerView.Adapter<FriendListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var itemImgHead : ImageView = view.findViewById(R.id.item_img_head)
        var itemName : TextView = view.findViewById(R.id.item_name)
        var itemImgGender : ImageView = view.findViewById(R.id.item_img_gender)
        var itemLocation : TextView = view.findViewById(R.id.item_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_friend_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friendList[position]
        Glide.with(MyApplication.context).load(friend.avatar).into(holder.itemImgHead)
        holder.itemName.text = friend.nickname
        if (friend.gender == 0) holder.itemImgGender.setImageResource(R.drawable.ic_friend_girl)
        else holder.itemImgGender.setImageResource(R.drawable.ic_friend_boy)
        val location : String =
            if (friend.province == friend.city) friend.country + friend.province
            else friend.country + friend.province + friend.city
        holder.itemLocation.text = location
    }

    override fun getItemCount() = friendList.size


}