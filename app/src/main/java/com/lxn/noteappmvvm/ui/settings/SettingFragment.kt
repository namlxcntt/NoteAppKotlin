package com.lxn.noteappmvvm.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.lxn.noteappmvvm.R
import com.lxn.noteappmvvm.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment : BaseFragment() {
    private lateinit var buttonBack: ImageView
    private lateinit var switch_button: Switch
    private lateinit var navController: NavController
    private lateinit var switchButtonChangeType: Switch
    var typeView: Boolean = false

    companion object {
        private val nighMode: String = "NightMode"
        private val viewType = "TypeView"
        private val sprName: String = "AppSettingPrefs"
    }

    override fun getViewResource(): Int {
        return R.layout.fragment_setting
    }

    override fun setUp() {
        navController = Navigation.findNavController(requireView())
        switch_button = requireView().findViewById(R.id.switch_button)
        switchButtonChangeType = requireView().findViewById(R.id.switch_button_changetype)
        val appSettingPrefs: SharedPreferences =
            requireActivity().getSharedPreferences(sprName, 0)
        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean(nighMode, false)
        buttonBack = requireView().findViewById(R.id.button_back)
        buttonBack.setOnClickListener { requireActivity().onBackPressed() }
        switch_button.isChecked = isNightModeOn
        switch_button.setOnCheckedChangeListener { view, isChecked ->
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefsEdit.putBoolean(nighMode, false)
                sharedPrefsEdit.apply()
                navController.navigate(R.id.action_nav_setting_to_nav_main)
                !isChecked
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefsEdit.putBoolean(nighMode, true)
                sharedPrefsEdit.apply()
                isChecked
                navController.navigate(R.id.action_nav_setting_to_nav_main)
            }
        }

        // fasle - > Stagen
        // true -> List
        typeView = appSettingPrefs.getBoolean(viewType, false)
        switchButtonChangeType.isChecked = typeView
        if (typeView)tv_viewtype.text ="List" else tv_viewtype.text ="Grid"


        switchButtonChangeType.setOnCheckedChangeListener { buttonView, isChecked ->
            if (typeView) {
                sharedPrefsEdit.putBoolean(viewType, false)
                sharedPrefsEdit.apply()
                navController.navigate(R.id.action_nav_setting_to_nav_main)
                !isChecked

            } else {
                sharedPrefsEdit.putBoolean(viewType, true)
                sharedPrefsEdit.apply()
                navController.navigate(R.id.action_nav_setting_to_nav_main)

            }
        }


    }

}