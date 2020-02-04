package com.myToDoList.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myToDoList.constants.Constants
import com.myToDoList.data.DbHandler
import com.myToDoList.model.Task
import com.myToDoList.ui.Adaptes.TaskAdapter
import com.myToDoList.utils.GridItemDecoration
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.ArrayList
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.R
import android.util.Base64


class DashboardActivity : AppCompatActivity() {
    private var list = ArrayList<Task>()
    lateinit var tasksRecyclerView: RecyclerView
    private lateinit var manager: GridLayoutManager
    private lateinit var profilePic: String
    private lateinit var profileName: String
    private var adapter: TaskAdapter? = null
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.myToDoList.R.layout.activity_dashboard)

        var dbHandler = DbHandler.getInstance(applicationContext)
        list = dbHandler.getTasks()
        tasksRecyclerView = findViewById(com.myToDoList.R.id.tasksRecyclerView)

        Log.e("Saved tasks are:", list.toString())
        noTasks.setText(list.size.toString() + " tasks.")
        if (list.isEmpty()) {
            relative_empty_task.setVisibility(View.VISIBLE)

        }

        this.sharedPreferences = this.getSharedPreferences(Constants.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        this.profilePic = sharedPreferences?.getString("profileImage", null).toString()
        this.profileName = sharedPreferences?.getString("profileName", null).toString()

        profile_Name?.setText(profileName)
        userProfPic?.setImageBitmap(decodeBase64(profilePic))

        createTask.setOnClickListener {


            intent = Intent(applicationContext, CreateNewTaskActivity::class.java)
            startActivity(intent)

        }

        userProfPic.setOnClickListener {
            intent = Intent(applicationContext, SetProfileActivity::class.java)
            startActivity(intent)
        }

        back.setOnClickListener({onBackPressed();})

        manager = GridLayoutManager(this, 2)
        tasksRecyclerView.layoutManager = this.manager
        adapter = TaskAdapter(this)
        tasksRecyclerView.adapter = adapter
        tasksRecyclerView.addItemDecoration(GridItemDecoration(this, 2,20))
        adapter?.setData(list)

    }

    fun decodeBase64(input: String): Bitmap {
        val decodedByte = Base64.decode(input, 0)
        return BitmapFactory
            .decodeByteArray(decodedByte, 0, decodedByte.size)
    }

}
