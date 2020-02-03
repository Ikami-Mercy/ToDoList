package com.myToDoList.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myToDoList.R
import com.myToDoList.data.DbHandler
import com.myToDoList.model.Task
import com.myToDoList.ui.Adaptes.TaskAdapter
import com.myToDoList.utils.GridItemDecoration
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.ArrayList

class DashboardActivity : AppCompatActivity() {
    private var list = ArrayList<Task>()
    lateinit var tasksRecyclerView: RecyclerView
    private lateinit var manager: GridLayoutManager
    private var adapter: TaskAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        var dbHandler = DbHandler.getInstance(applicationContext)
        list = dbHandler.getTasks()
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView)

        Log.e("Saved tasks are:", list.toString())
        noTasks.setText(list.size.toString() + " tasks.")
        if (list.isEmpty()) {
            relative_empty_task.setVisibility(View.VISIBLE)

        }
        createTask.setOnClickListener {


            intent = Intent(applicationContext, CreateNewTaskActivity::class.java)
            startActivity(intent)

        }

        userProfPic.setOnClickListener {
            intent = Intent(applicationContext, ProfileActivity::class.java)
            startActivity(intent)
        }


        manager = GridLayoutManager(this, 2)
        tasksRecyclerView.layoutManager = manager
        adapter = TaskAdapter(this)
        tasksRecyclerView.adapter = adapter
        tasksRecyclerView.addItemDecoration(GridItemDecoration(this, 2,20))
        adapter?.setData(list)

    }


}
