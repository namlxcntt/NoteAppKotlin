package com.lxn.noteappmvvm.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.lxn.noteappmvvm.R
import com.lxn.noteappmvvm.base.BaseFragment
import com.suke.widget.SwitchButton


class SettingFragment : BaseFragment() {
    private lateinit var buttonBack: ImageView
    private lateinit var switch_button: SwitchButton
    private lateinit var navController: NavController
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun getViewResource(): Int {
        return R.layout.fragment_setting
    }

    override fun setUp() {
        navController = Navigation.findNavController(requireView())
        switch_button = requireView().findViewById(R.id.switch_button)
        val appSettingPrefs: SharedPreferences =
            requireActivity().getSharedPreferences("AppSettingPrefs", 0)
        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)

        buttonBack = requireView().findViewById(R.id.button_back)
        buttonBack.setOnClickListener { requireActivity().onBackPressed() }

        if (isNightModeOn) {
            switch_button.toggle(false)
        } else {
            switch_button.toggle(true)
        }

        switch_button.setOnCheckedChangeListener { view, isChecked ->
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefsEdit.putBoolean("NightMode", false)
                sharedPrefsEdit.apply()
                navController.navigate(R.id.action_nav_setting_to_nav_main)
                !isChecked

                Toast.makeText(context, "Disable Dark Mode", Toast.LENGTH_SHORT)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefsEdit.putBoolean("NightMode", true)
                sharedPrefsEdit.apply()
                isChecked
                navController.navigate(R.id.action_nav_setting_to_nav_main)
                Toast.makeText(context, "Enable Dark Mode", Toast.LENGTH_SHORT)
            }
        }


    }

}