package com.lxn.noteappmvvm

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.app_bar_main.*
import me.ibrahimsn.lib.SmoothBottomBar


class MainActivity : AppCompatActivity() {

    //    private lateinit var appBarConfiguration: AppBarConfiguration\

    lateinit var callback: ChangeLayoutManager
    lateinit var navController: NavController
    lateinit var appSettingPrefs: SharedPreferences

    public fun setOnHeadlineSelectedListener(callback: ChangeLayoutManager) {
        this.callback = callback
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appSettingPrefs = getSharedPreferences("AppSettingPrefs", 0)
        navController = findNavController(R.id.nav_host_fragment)
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)
        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
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