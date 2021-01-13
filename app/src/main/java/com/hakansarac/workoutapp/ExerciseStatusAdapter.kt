package com.hakansarac.workoutapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_exercise_status.view.*

class ExerciseStatusAdapter(val context : Context,val items : ArrayList<Exercise>) : RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseStatusAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_exercise_status,parent,false))
    }

    override fun onBindViewHolder(holder: ExerciseStatusAdapter.ViewHolder, position: Int) {
        val model : Exercise = items[position]
        holder.texViewItem.text = model.getId().toString()
        if(model.getIsSelected()) {
            holder.texViewItem.background = ContextCompat.getDrawable(
                context,
                R.drawable.item_circular_thin_color_accent_border
            )
            holder.texViewItem.setTextColor(Color.parseColor("#121212"))
        }else if(model.getIsCompleted()) {
            holder.texViewItem.background = ContextCompat.getDrawable(
                context,
                R.drawable.item_circular_color_accent_background
            )
            holder.texViewItem.setTextColor(Color.parseColor("#FFFFFF"))
        }else{
            holder.texViewItem.background = ContextCompat.getDrawable(
                context,
                R.drawable.item_circular_color_gray_bg
            )
            holder.texViewItem.setTextColor(Color.parseColor("#121212"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val texViewItem = view.textViewItem
    }
}