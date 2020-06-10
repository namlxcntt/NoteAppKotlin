package com.lxn.noteappmvvm

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import me.ibrahimsn.lib.SmoothBottomBar


class MainActivity : AppCompatActivity() {

    //    private lateinit var appBarConfiguration: AppBarConfiguration\
    public lateinit var bottomBar: SmoothBottomBar

    lateinit var callback: ChangeLayoutManager
    lateinit var navController: NavController
    lateinit var frameBottom: FrameLayout

    public fun setOnHeadlineSelectedListener(callback: ChangeLayoutManager) {
        this.callback = callback
    }

    override fun onResume() {
        frameBottom.visibility = View.VISIBLE
        super.onResume()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomBar = findViewById(R.id.bottomBar)
        frameBottom = findViewById(R.id.frame_bottom)

        navController = findNavController(R.id.nav_host_fragment)
        bottomBar.onItemSelected = {
            if (it == 0) {
                navController.navigate(R.id.nav_main)
            } else if (it == 1) {
                navController.navigate(R.id.nav_search)
            } else if (it == 2) {
                navController.navigate(R.id.nav_setting)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        bottomBar.setupWithNavController(menu!!, navController)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return when (item.itemId) {
            R.id.ic_main -> {
                Toast.makeText(this, "Click click", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.nav_main, null)
                true
            }
            R.id.ic_search -> {
                Toast.makeText(this, "Click click", Toast.LENGTH_SHORT).show()

                true
            }
            R.id.ic_settings -> {
                Toast.makeText(this, "Click click", Toast.LENGTH_SHORT).show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


    interface ChangeLayoutManager {
        fun changeLayout(type: Int)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

}