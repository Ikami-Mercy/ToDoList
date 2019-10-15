package com.myToDoList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class DisplayToDoListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_to_do_list)


/*
        private fun setUpVp() {

            Handler().post {
                val viewpagerAdapter = ViewpagerAdapter(supportFragmentManager)
                viewpagerAdapter.addFragment(ChatFragment(), "Chats")

                viewpagerAdapter.addFragment(GroupFragment(), "Groups")

                // viewpagerAdapter.addFragment(new ContactFragment(), "Contacts");
                viewpagerAdapter.addFragment(CallsFragment(), "Calls")

                viewpager.setAdapter(viewpagerAdapter)

                if (vpTab == 1) {

                    viewpager.setCurrentItem(1)
                }


                if (vpTab == 2) {

                    viewpager.setCurrentItem(2)
                }
            }

            chattabs.setupWithViewPager(viewpager)
            //animate FABs

            chattabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener() {
                fun onTabSelected(tab: TabLayout.Tab) {
                    viewpager.setCurrentItem(tab.getPosition())
                    animateFab(tab.getPosition())
                }

                fun onTabUnselected(tab: TabLayout.Tab) {

                }

                fun onTabReselected(tab: TabLayout.Tab) {

                }
            })

            val onPageChangeListener = object : ViewPager.OnPageChangeListener() {
                fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                fun onPageSelected(position: Int) {
                    animateFab(position)
                }

                fun onPageScrollStateChanged(state: Int) {

                }
            }
        }*/
    }
}
