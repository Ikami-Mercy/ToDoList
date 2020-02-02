package com.myToDoList.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.myToDoList.R
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        createTask.setOnClickListener{


            intent =  Intent(applicationContext, CreateNewTaskActivity::class.java)
            startActivity(intent)

        }

        userProfPic.setOnClickListener {
            intent =  Intent(applicationContext, ProfileActivity::class.java)
            startActivity(intent)
        }
    }



}
