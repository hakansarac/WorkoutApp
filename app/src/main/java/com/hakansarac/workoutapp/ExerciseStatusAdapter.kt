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
    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseStatusAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_exercise_status,parent,false))
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: ExerciseStatusAdapter.ViewHolder, position: Int) {
        val model : Exercise = items[position]
        holder.texViewItem.text = model.getId().toString()
        // Updating the background and text color according to the flags what is in the list.
        // A link to set text color programmatically and same way we can set the drawable background also instead of color.
        // https://stackoverflow.com/questions/8472349/how-to-set-text-color-to-a-text-view-programmatically
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