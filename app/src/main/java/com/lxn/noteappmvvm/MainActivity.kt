package com.lxn.noteappmvvm

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI.navigateUp
import kotlinx.android.synthetic.main.app_bar_main.*
import me.ibrahimsn.lib.OnItemSelectedListener
import me.ibrahimsn.lib.SmoothBottomBar

class MainActivity : AppCompatActivity() {

    //    private lateinit var appBarConfiguration: AppBarConfiguration\
    public lateinit var bottomBar: SmoothBottomBar

    lateinit var callback: ChangeLayoutManager
    lateinit var navController: NavController

    public fun setOnHeadlineSelectedListener(callback: ChangeLayoutManager) {
        this.callback = callback
    }

    override fun onResume() {
        bottomBar.visibility = View.VISIBLE
        super.onResume()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomBar = findViewById(R.id.bottomBar)

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

        val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED) // It can be done by show_forced too

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

}