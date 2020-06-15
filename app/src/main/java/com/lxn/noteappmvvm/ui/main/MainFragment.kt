package com.lxn.noteappmvvm.ui.main

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lxn.noteapp.adapter.NoteAdapter
import com.lxn.noteapp.model.Note
import com.lxn.noteappmvvm.R
import com.lxn.noteappmvvm.base.BaseFragment
import com.vicpin.krealmextensions.delete
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
    private var doubleBackToExitPressedOnce = false

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

    override fun onDetach() {
        super.onDetach()
//        requireView().isFocusableInTouchMode = true
//        requireView().setOnKeyListener { v, keyCode, event ->
//            return@setOnKeyListener false
//        }
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
//        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val adapter = recycleview_main.adapter as NoteAdapter
//                adapter.removeAt(viewHolder.adapterPosition)
//            }
//        }
//        val itemTouchHelper = ItemTouchHelper(swipeHandler)

        if (viewType) {
//            itemTouchHelper.attachToRecyclerView(recycleview_main)
            noteAdapter.setmType(1)
            recycleview_main.layoutManager = LinearLayoutManager(context)
            noteAdapter.notifyDataSetChanged()
        } else {
//            itemTouchHelper.attachToRecyclerView(null);
            recycleview_main.layoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            noteAdapter.notifyDataSetChanged()
        }
        view!!.isFocusableInTouchMode = true
        view!!.setOnKeyListener { v, keyCode, event ->
            keyCode == KeyEvent.KEYCODE_BACK
            if (doubleBackToExitPressedOnce) {
                activity!!.moveTaskToBack(true);
                activity!!.finish()
            }
            doubleBackToExitPressedOnce = true
            Toast.makeText(requireContext(), "Please click BACK again to exit", Toast.LENGTH_SHORT)
                .show()

            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        }
//        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
//        itemTouchHelper.attachToRecyclerView(recycleview_main)


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


    var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            val position = viewHolder.adapterPosition
            var note = notesList[position]
            notesList.remove(note)
            Note().delete { equalTo("id", note.id) }
            getAllTodo()
        }
    }


}
