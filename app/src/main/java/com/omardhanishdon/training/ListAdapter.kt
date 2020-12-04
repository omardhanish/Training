package com.omardhanishdon.training

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ListAdapter(val context : Context , val contactList : MutableList<MainActivity.Information>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    interface CallBack {
        fun onEdit(pos : Int)
    }

    var callBack : CallBack? = null

    fun setCallBackListener(callBack: CallBack){
        this.callBack = callBack
    }

    inner class ViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        val name : TextView
        val number : TextView
        val delete : Button
        val edit : Button

        init {
            name = view.findViewById(R.id.name)
            number = view.findViewById(R.id.number)
            delete = view.findViewById(R.id.delete)
            edit = view.findViewById(R.id.edit)

            delete.setOnClickListener {
                contactList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }

            edit.setOnClickListener {
                callBack?.onEdit(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
       return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = contactList.get(position).name
        holder.number.text = contactList.get(position).number
    }

}