package com.lxn.noteappmvvm.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lxn.noteapp.adapter.NoteAdapter
import com.lxn.noteapp.model.Note
import com.lxn.noteappmvvm.R
import com.lxn.noteappmvvm.base.BaseFragment
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.queryAll

import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainFragment : BaseFragment(), NoteAdapter.OnItemClickNote, View.OnClickListener,
    View.OnLongClickListener {
    private lateinit var noteAdapter: NoteAdapter
    private var notesList = ArrayList<Note>()
    var mType = 1
    private lateinit var navController: NavController
    lateinit var appSettingPrefs: SharedPreferences
    lateinit var sharedPrefsEdit: SharedPreferences.Editor

    companion object {
        val keyBundle: String = "Key"
        private val viewType = "TypeView"
        private val sprName: String = "AppSettingPrefs"
    }

    override fun getViewResource(): Int {
        return R.layout.fragment_main
    }

    override fun setUp() {
        initView()
        getAllTodo()
        getGreetingMessage()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun initView() {
        noteAdapter = NoteAdapter(activity, this)
        navController = Navigation.findNavController(requireView())
        recycleview_main.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recycleview_main.adapter = noteAdapter
        button_search.setOnClickListener(this)
        button_setting.setOnClickListener(this)
        addNotes.setOnLongClickListener(this)
        addNotes.setOnClickListener(this)
        appSettingPrefs = activity!!.getSharedPreferences(sprName, 0)
        sharedPrefsEdit = appSettingPrefs.edit()
        val viewType: Boolean = appSettingPrefs.getBoolean(viewType, false)
        if (viewType) {
            noteAdapter.setmType(1)
            recycleview_main.layoutManager = LinearLayoutManager(context)
            noteAdapter.notifyDataSetChanged()

        }
       else{
            recycleview_main.layoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            noteAdapter.notifyDataSetChanged()
        }

    }

    private fun getAllTodo() {
        notesList = Note().queryAll() as ArrayList<Note>
        noteAdapter.setList(notesList)
        if (notesList.size == 0) {
            gif_dinasour.visibility = View.VISIBLE
            tv_null_note.visibility = View.VISIBLE
        } else {
            gif_dinasour.visibility = View.INVISIBLE
            tv_null_note.visibility = View.INVISIBLE
        }
        tv_number_note.text = " ${notesList.size} Notes"
    }

    override fun oItemClickNote(note: Note) {
        var bundle = Bundle()
        bundle.putParcelable(keyBundle, note)
        navController!!.navigate(R.id.action_nav_main_to_nav_detailnote, bundle)

    }

    override fun callBackDelete() {
        getAllTodo()
    }

    override fun onItemNotes(notes: Int) {
        tv_number_note.text = " $notes Notes"
        if (notes > 0) {
            gif_dinasour.visibility = View.INVISIBLE
            tv_null_note.visibility = View.INVISIBLE
        } else {
            gif_dinasour.visibility = View.VISIBLE
            tv_null_note.visibility = View.VISIBLE
        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.addNotes -> navController.navigate(R.id.action_nav_main_to_nav_addnote)

            R.id.button_search -> navController.navigate(R.id.action_nav_main_to_nav_search)

            R.id.button_setting -> navController.navigate(R.id.action_nav_main_to_nav_setting)
        }
    }

    override fun onLongClick(v: View?): Boolean {
        when (v!!.id) {
            R.id.addNotes -> {
                deleteAll<Note>()
                getAllTodo()
                tv_number_note.text = " 0 Notes"
                gif_dinasour.visibility = View.VISIBLE
                tv_null_note.visibility = View.VISIBLE

            }
        }
        return true
    }
    fun getGreetingMessage(): Unit {
        val c = Calendar.getInstance()
        val timeOfDay = c.get(Calendar.HOUR_OF_DAY)
        when (timeOfDay) {
            in 0..11 -> tv_hello.text = "Hey, Good \nMorning !"
            in 12..17 -> tv_hello.text = "Hey, Good \nAfternoon !"
            in 17..21 -> tv_hello.text = "Hey, Good \nEvening !"
            in 21..23 -> tv_hello.text = "Hey, Good \nNight !"
            else -> "Hello"
        }
    }

}
