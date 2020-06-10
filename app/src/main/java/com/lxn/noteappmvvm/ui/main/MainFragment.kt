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
    var mType = 1
    private lateinit var constrainsTop: ConstraintLayout


    override fun onResume() {
        super.onResume()
        constrainsTop.visibility = View.VISIBLE
    }

    override fun onDetach() {
        super.onDetach()
//        constrainsTop.visibility = View.GONE
    }

    private lateinit var navController: NavController
    override fun getViewResource(): Int {
        return R.layout.fragment_main
    }

    override fun setUp() {
        constrainsTop = requireView().findViewById(R.id.constrain_top)
        addNotes = requireView().findViewById(R.id.addNotes)
        recyclerView = requireView().findViewById(R.id.recycleview_main)
        buttonSearch = requireView().findViewById(R.id.button_search)
        buttonSettings = requireView().findViewById(R.id.button_setting)
        realm = Realm.getDefaultInstance()
        noteAdapter = NoteAdapter(activity, this)
        (activity as MainActivity?)?.setOnHeadlineSelectedListener(this)
        navController = Navigation.findNavController(requireView())
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = noteAdapter
        addNotes.setOnClickListener {
            navController!!.navigate(R.id.action_nav_main_to_nav_addnote)
        }
        getAllTodo()
        addNotes.setOnLongClickListener {
            deleteAll<Note>()
            getAllTodo()
            return@setOnLongClickListener true
        }
        buttonSearch.setOnClickListener {
            navController.navigate(R.id.action_nav_main_to_nav_search)
        }
        buttonSettings.setOnClickListener {
            navController.navigate(R.id.action_nav_main_to_nav_setting)
        }


    }

    private fun getAllTodo() {
        notesList = Note().queryAll() as ArrayList<Note>
        noteAdapter.setList(notesList)


    }


    override fun onDestroy() {
        super.onDestroy()
    }


    override fun oItemClickNote(note: Note) {
        var bundle = Bundle()
        bundle.putParcelable("Key", note)
        navController!!.navigate(R.id.action_nav_main_to_nav_detailnote, bundle)

    }


    override fun callBackDelete() {
        getAllTodo()
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
