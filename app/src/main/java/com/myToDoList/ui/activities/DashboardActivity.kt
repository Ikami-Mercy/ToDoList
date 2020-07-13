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
import com.myToDoList.ui.Adapters.TaskAdapter
import com.myToDoList.utils.GridItemDecoration
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.ArrayList
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.util.Base64
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.infideap.drawerbehavior.Advance3DDrawerLayout
import com.myToDoList.R
import com.myToDoList.fingerprint.FingerprintLockActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*

class DashboardActivity : AppCompatActivity() {
    private var list = ArrayList<Task>()
    lateinit var tasksRecyclerView: RecyclerView
    private lateinit var manager: GridLayoutManager
    private var profilePic: String? = null
    private var profileName: String? = null
    private var firstrun: Boolean? = true
    private var lockCheck: Boolean? = true
    private var adapter: TaskAdapter? = null
   // private var navigationView: NavigationView? = null
   // private var drawer_layout: Advance3DDrawerLayout? = null
    private var navHeader: View? = null
    private var drawerToggle :ActionBarDrawerToggle ? =null
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.sharedPreferences =
            this.getSharedPreferences(Constants.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        this.profilePic = sharedPreferences?.getString("profileImage", null).toString()
        this.profileName = sharedPreferences?.getString("profileName", null).toString()
        this.firstrun = sharedPreferences?.getBoolean("firstrun", true)
        this.lockCheck = sharedPreferences?.getBoolean(Constants.LOCKED, true)

        if (firstrun!!) {

            launchActivity(SetProfileActivity::class.java)

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
               // drawer_profile_image.setImageBitmap(decodeBase64(it))
            }


            start_new_task.setOnClickListener {


                launchActivity(CreateNewTaskActivity::class.java)

            }

            createTask.setOnClickListener {

                launchActivity(CreateNewTaskActivity::class.java)


            }

            userProfPic.setOnClickListener {

                launchActivity(SetProfileActivity::class.java)


            }


            manager = GridLayoutManager(this, 2)
            tasksRecyclerView.layoutManager = this.manager
            adapter = TaskAdapter(this)
            tasksRecyclerView.adapter = adapter
            tasksRecyclerView.addItemDecoration(GridItemDecoration(this, 2, 20))
            adapter?.setData(list)
            adapter?.notifyDataSetChanged()

            navHeader = nav_view?.getHeaderView(0)
            drawerToggle = ActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            drawerToggle?.getDrawerArrowDrawable()?.setColor(Color.parseColor("#FFFFFF"))
            drawer_layout?.addDrawerListener(drawerToggle!!)
            drawerToggle?.syncState()
            drawer_layout?.useCustomBehavior(GravityCompat.START) //assign custom behavior for "Left" drawer
            drawer_layout?.useCustomBehavior(GravityCompat.END)
            drawer_layout?.setViewRotation(GravityCompat.START, 15f)
            drawer_layout?.setViewScale(
                GravityCompat.START,
                0.9f
            ) //set height scale for main view (0f to 1f)
            drawer_layout?.setViewElevation(
                GravityCompat.START,
                20f
            ) //set main view elevation when drawer open (dimension)
            drawer_layout?.setViewScrimColor(
                GravityCompat.START,
                Color.TRANSPARENT
            ) //set drawer overlay coloe (color)
            drawer_layout?.setDrawerElevation(GravityCompat.START, 20f) //set drawer elevation (dimension)
            drawer_layout?.setContrastThreshold(3f) //set maximum of contrast ratio between white text and background color.
            drawer_layout?.setRadius(
                GravityCompat.START,
                25f
            ) //set end container's corner radius (dimension)

            var textProfName = navHeader?.findViewById(R.id.drawer_profile_Name) as TextView
            var imgProfImage = navHeader?.findViewById(R.id.drawer_profile_image) as ImageView

            profileName?.let {
                textProfName.setText(it)
            }

            profilePic?.let {
                if (it == null)
                    return
                imgProfImage.setImageBitmap(decodeBase64(it))
            }

            nav_view.setNavigationItemSelectedListener(
                object : NavigationView.OnNavigationItemSelectedListener {
                    override fun onNavigationItemSelected(item: MenuItem): Boolean {
                        val id = item.itemId
                        when (id) {


                            R.id.nav_privacy -> {

                                launchActivity(FingerprintLockActivity::class.java)

                            }
                            R.id.nav_feedback -> {
                                sendEmail("devtindi@gmail.com", "Notepad Pro Feedback", "")
                            }


                            else -> return true
                        }

                        drawer_layout.closeDrawer(GravityCompat.START)
                        return true

                    }
                })


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


    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }

    private fun sendEmail(recipient: String, subject: String, message: String) {
        /*ACTION_SEND action to launch an email client installed on your Android device.*/
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/message"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        //put the Subject in the intent
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        //put the message in the intent
        mIntent.putExtra(Intent.EXTRA_TEXT, message)


        try {
            //start email intent
           startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){

            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }

    }
    private fun launchActivity(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
       // finish()
    }


}
