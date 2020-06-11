package com.lxn.noteappmvvm.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.lxn.noteapp.adapter.NoteAdapter
import com.lxn.noteapp.model.Note
import com.lxn.noteappmvvm.MainActivity
import com.lxn.noteappmvvm.R
import com.lxn.noteappmvvm.base.BaseFragment
import com.lxn.noteappmvvm.ui.detailnote.DetailNoteFragment
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.queryAll
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : BaseFragment(), NoteAdapter.OnItemClickNote, MainActivity.ChangeLayoutManager {
    lateinit var addNotes: TextView
    lateinit var recyclerView: RecyclerView
    private lateinit var realm: Realm
    private lateinit var noteAdapter: NoteAdapter
    private var notesList = ArrayList<Note>()
    private lateinit var buttonSearch: ImageView
    private lateinit var buttonSettings: ImageView
    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var tvNull: TextView
    var firstVisibleInListview: Int = 0
    var mType = 1


    //    private lateinit var constrainsTop: ConstraintLayout
    private lateinit var tvHello: TextView


    private lateinit var navController: NavController
    override fun getViewResource(): Int {
        return R.layout.fragment_main
    }

    fun getGreetingMessage(): Unit {
        val c = Calendar.getInstance()
        val timeOfDay = c.get(Calendar.HOUR_OF_DAY)
        when (timeOfDay) {
            in 0..11 -> tvHello.text = "Hey, Good \nMorning !"
            in 12..17 -> tvHello.text = "Hey, Good \nAfternoon !"
            in 17..21 -> tvHello.text = "Hey, Good \nEvening !"
            in 21..23 -> tvHello.text = "Hey, Good \nNight !"
            else -> "Hello"
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setUp() {
        addNotes = requireView().findViewById(R.id.addNotes)
        recyclerView = requireView().findViewById(R.id.recycleview_main)
        buttonSearch = requireView().findViewById(R.id.button_search)
        buttonSettings = requireView().findViewById(R.id.button_setting)
        lottieAnimationView = requireView().findViewById(R.id.gif_dinasour)
        tvNull = requireView().findViewById(R.id.tv_null_note)
        tvHello = requireView().findViewById(R.id.tv_hello)

        realm = Realm.getDefaultInstance()
        noteAdapter = NoteAdapter(activity, this)
        (activity as MainActivity?)?.setOnHeadlineSelectedListener(this)
        navController = Navigation.findNavController(requireView())

        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = noteAdapter
        onScrollViewRecycleView()
        addNotes.setOnClickListener {
            navController!!.navigate(R.id.action_nav_main_to_nav_addnote)
        }
        getAllTodo()
        addNotes.setOnLongClickListener {
            deleteAll<Note>()
            getAllTodo()
            tv_number_note.text = " 0 Notes"
            lottieAnimationView.visibility = View.VISIBLE
            tvNull.visibility = View.VISIBLE
            return@setOnLongClickListener true
        }
        buttonSearch.setOnClickListener {
            navController.navigate(R.id.action_nav_main_to_nav_search)
        }
        buttonSettings.setOnClickListener {
            navController.navigate(R.id.action_nav_main_to_nav_setting)
        }
        getGreetingMessage()

    }

    private fun onScrollViewRecycleView() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun getAllTodo() {
        notesList = Note().queryAll() as ArrayList<Note>
        noteAdapter.setList(notesList)
        if (notesList.size == 0) {
            lottieAnimationView.visibility = View.VISIBLE
            tvNull.visibility = View.VISIBLE
        } else {
            lottieAnimationView.visibility = View.INVISIBLE
            tvNull.visibility = View.INVISIBLE
        }
        tv_number_note.text = " ${notesList.size} Notes"

    }

    override fun oItemClickNote(note: Note) {
        var bundle = Bundle()
        bundle.putParcelable("Key", note)
        navController!!.navigate(R.id.action_nav_main_to_nav_detailnote, bundle)

    }


    override fun callBackDelete() {
        getAllTodo()

    }

    override fun onItemNotes(notes: Int) {
        tv_number_note.text = " $notes Notes"
        if (notes > 0) {
            lottieAnimationView.visibility = View.INVISIBLE
            tvNull.visibility = View.INVISIBLE
        } else {
            lottieAnimationView.visibility = View.VISIBLE
            tvNull.visibility = View.VISIBLE
        }

    }

    override fun changeLayout(type: Int) {
        noteAdapter.setmType(type)
        if (type == 1) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            noteAdapter.notifyDataSetChanged()

        } else {
            recyclerView.layoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            noteAdapter.notifyDataSetChanged()
        }
    }


}
