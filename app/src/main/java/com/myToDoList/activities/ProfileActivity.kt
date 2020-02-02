package com.myToDoList.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.myToDoList.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        iv_profile_avatar.setOnClickListener{

        }
    }
}
