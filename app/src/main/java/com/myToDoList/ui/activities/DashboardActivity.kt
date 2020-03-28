package com.myToDoList.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myToDoList.constants.Constants
import com.myToDoList.data.DbHandler
import com.myToDoList.model.Task
import com.myToDoList.ui.Adaptes.TaskAdapter
import com.myToDoList.utils.GridItemDecoration
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.ArrayList
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Base64

class DashboardActivity : AppCompatActivity() {
    private var list = ArrayList<Task>()
    lateinit var tasksRecyclerView: RecyclerView
    private lateinit var manager: GridLayoutManager
    private var profilePic: String? = null
    private var profileName: String? = null
    private var firstrun: Boolean? = true
    private var adapter: TaskAdapter? = null
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.myToDoList.R.layout.activity_dashboard)


        this.sharedPreferences =
            this.getSharedPreferences(Constants.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        this.profilePic = sharedPreferences?.getString("profileImage", null).toString()
        this.profileName = sharedPreferences?.getString("profileName", null).toString()
        this.firstrun = sharedPreferences?.getBoolean("firstrun", true)

        if (firstrun!!) {

            intent = Intent(applicationContext, SetProfileActivity::class.java)
            startActivity(intent)
        } else {
            var dbHandler = DbHandler.getInstance(applicationContext)
            list = dbHandler.getTasks()
            tasksRecyclerView = findViewById(com.myToDoList.R.id.tasksRecyclerView)

            Log.e("Saved tasks are:", list.toString())
            noTasks.setText(list.size.toString() + " tasks.")
            if (list.isEmpty()) {
                relative_empty_task.setVisibility(View.VISIBLE)

            }

            profileName?.let {
                profile_Name.setText(it + "'s profile")
            }
            Log.e("profilePic:", profilePic)

            profilePic?.let {
                Log.e("profilePic:", "letting")
                if (it == null)
                    return
                userProfPic.setImageBitmap(decodeBase64(it))
            }



            createTask.setOnClickListener {


                intent = Intent(applicationContext, CreateNewTaskActivity::class.java)
                startActivity(intent)

            }

            userProfPic.setOnClickListener {
                intent = Intent(applicationContext, SetProfileActivity::class.java)
                startActivity(intent)
                finish()

            }

            back.setOnClickListener({

                /* intent = Intent(applicationContext, com.infide::class.java)
                 startActivity(intent)*/
                //  onBackPressed();
            })

            manager = GridLayoutManager(this, 2)
            tasksRecyclerView.layoutManager = this.manager
            adapter = TaskAdapter(this)
            tasksRecyclerView.adapter = adapter
            tasksRecyclerView.addItemDecoration(GridItemDecoration(this, 2, 20))
            adapter?.setData(list)
            adapter?.notifyDataSetChanged()



        }
    }

    fun decodeBase64(input: String): Bitmap {
        val decodedByte = Base64.decode(input, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
    }

    override fun onResume() {
        super.onResume()
        adapter?.setData(list)
        adapter?.notifyDataSetChanged()

    }
}
