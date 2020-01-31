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


            Toast.makeText(applicationContext, "We are here!!", Toast.LENGTH_LONG).show()
            intent =  Intent(applicationContext, CreateNewTaskActivity::class.java)
            startActivity(intent)

        }
    }



}
