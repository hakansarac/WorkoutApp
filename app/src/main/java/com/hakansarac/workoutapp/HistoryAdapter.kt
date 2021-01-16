package com.hakansarac.workoutapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history_row.view.*

class HistoryAdapter(val context: Context,val items: ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val linearLayoutHistoryMainItem = view.linearLayoutHistoryItemMain
        val textViewItem = view.textViewItem
        val textViewPosition = view.textViewPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history_row,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date : String = items.get(position)

        holder.textViewPosition.text = (position+1).toString()    //position starts from zero. therefore, we add 1 to position
        holder.textViewItem.text = date

        if(position%2 == 0){
            holder.linearLayoutHistoryMainItem.setBackgroundColor(Color.parseColor("#ECECEC"))
        }else{
            holder.linearLayoutHistoryMainItem.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}