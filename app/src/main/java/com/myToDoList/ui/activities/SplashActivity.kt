package com.myToDoList.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.myToDoList.R
import com.myToDoList.constants.Constants
import com.myToDoList.fingerprint.FingerprintLockActivity
import com.myToDoList.fingerprint.PasswordActivity
import com.rbddevs.splashy.Splashy

class SplashActivity : AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null
    private var lockCheck: Boolean? = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        this.sharedPreferences =
            this.getSharedPreferences(Constants.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        this.lockCheck = sharedPreferences?.getBoolean(Constants.LOCKED, false)
        setSplashy()

    }

    fun setSplashy(){
        Splashy(this)
            .setLogo(R.mipmap.logo)
            .setTitle("Notepad Pro.")
            .setTitleColor("#FFFFFF")
            .setAnimation(Splashy.Animation.SLIDE_IN_LEFT_BOTTOM,800)
            .setBackgroundColor("#16141A")
            .setSubTitle("Simple.Secure.Pro")
            .setSubTitleColor("#FFEB3B")
            .setProgressColor(R.color.white)
            .setFullScreen(true)
            .setTime(2000)
            .show()
        // Listener for completion of splash screen
        Splashy.onComplete(object : Splashy.OnComplete {
            override fun onComplete() {
                if(this@SplashActivity.lockCheck!!){
                    launchActivity(PasswordActivity::class.java)
                }
                else
                    launchActivity(DashboardActivity::class.java)
            }

        })
    }
    private fun launchActivity(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
        // finish()
    }
}
