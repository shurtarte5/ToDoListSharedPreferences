package com.hurtarte.listmaker2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hurtarte.listmaker2.model.TaskList

class ListItemsRecyclerViewAdapter(var list:TaskList):
    RecyclerView.Adapter<ListItemsRecyclerViewAdapter.ListItemViewHolder>() {


    inner class ListItemViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        val taskTextView= itemView.findViewById(R.id.textview_task) as TextView


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_view_holder,parent,false)

        return ListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.taskTextView.text=list.tasks[position]

    }

    override fun getItemCount(): Int {
        return list.tasks.size
    }
}