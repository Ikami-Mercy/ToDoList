package com.myToDoList.ui.Adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.myToDoList.R
import com.myToDoList.model.Task

import java.util.ArrayList

class AllTaskAdapter(private var context: Context) :
    RecyclerView.Adapter<AllTaskAdapter.HomeViewHolder>(), Filterable {

    private var list: ArrayList<Task>? = null


    /**
     * set data to adapter
     *
     * @param list
     */
    fun setData(list: ArrayList<Task>) {
        this.list = list
        notifyDataSetChanged()
    }


    override fun getFilter(): Filter? {
        return null
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTaskAdapter.HomeViewHolder {
        context = parent.context
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_item_task,
                null
            ), context
        )
    }

    override fun onBindViewHolder(holder: AllTaskAdapter.HomeViewHolder, position: Int) {

        val task = list!![position]
       // holder.task_date.text = timeStampFormated(Timestamp.valueOf(task.timestamp))
        holder.task_title.text = task.taskTittle

    }


    override fun getItemCount(): Int {
        return 0
    }

    inner class HomeViewHolder(private val mView: View, ctx: Context) :
        RecyclerView.ViewHolder(mView) {
        internal val task_date: TextView
        internal val task_title: TextView


        init {
            task_date = mView.findViewById(R.id.task_date)
            task_title = mView.findViewById(R.id.task_title)
        }
    }
}
