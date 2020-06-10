package com.lxn.noteappmvvm.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.lxn.noteappmvvm.R
import com.lxn.noteappmvvm.base.BaseFragment


class SettingFragment : BaseFragment() {
    private lateinit var buttonBack : ImageView


    override fun getViewResource(): Int {
        return R.layout.fragment_setting
    }

    override fun setUp() {
        buttonBack = view!!.findViewById(R.id.button_back)
        buttonBack.setOnClickListener { requireActivity().onBackPressed() }

    }

}