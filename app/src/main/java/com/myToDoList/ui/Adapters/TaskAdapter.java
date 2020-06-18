package com.myToDoList.ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myToDoList.R;
import com.myToDoList.data.DbHandler;
import com.myToDoList.model.Task;
import com.myToDoList.ui.activities.SingleTaskActivity;
import com.myToDoList.utils.TimeUtil;

import java.sql.Timestamp;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.HomeViewHolder> implements Filterable {

    private ArrayList<Task> list;
    private Context context;
    private DbHandler dbHandler;

    public TaskAdapter(Context context) {
        this.context = context;
        this.dbHandler = DbHandler.getInstance(this.context);

    }


    /**
     * set data to adapter
     *
     * @param list
     */
    public void setData(ArrayList<Task> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public Filter getFilter() {
        return null;
    }


    @NonNull
    @Override
    public TaskAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_task, null), context);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.HomeViewHolder holder, int position) {

        Task task = list.get(position);
        int taskType = task.getTaskType();
        Timestamp taskTimestamp = new Timestamp(task.getTimestamp());
        holder.task_date.setText(TimeUtil.timeStampFormated(taskTimestamp));
        holder.task_title.setText(task.getTaskTittle());
        String taskid = task.getTaskID();
        if(task.getTaskDone()==1){

            holder.task_title.setPaintFlags(holder.task_title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.mView.setOnClickListener(v -> {
            Log.e("Clicked", "task is" + taskid);
            Intent intent = new Intent(context, SingleTaskActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("taskID", taskid);
            intent.putExtras(bundle);
            context.startActivity(intent);

        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView task_date;
        private TextView task_title;


        public HomeViewHolder(@NonNull View itemView, final Context ctx) {
            super(itemView);

            this.mView = itemView;
            task_date = itemView.findViewById(R.id.task_date);
            task_title = itemView.findViewById(R.id.task_title);
        }
    }
}
