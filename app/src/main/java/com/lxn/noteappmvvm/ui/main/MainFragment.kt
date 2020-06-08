package com.lxn.noteappmvvm.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
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
import io.realm.Realm
import io.realm.RealmResults


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : BaseFragment(), NoteAdapter.OnItemClickNote, MainActivity.ChangeLayoutManager {
    lateinit var addNotes: ImageView
    lateinit var recyclerView: RecyclerView
    private lateinit var realm: Realm
    private lateinit var noteAdapter: NoteAdapter
    private var notesList = ArrayList<Note>()
    var mType = 1
    private var edtSearch: EditText? = null
    private var mTextSearch = ""

    private lateinit var navController: NavController
    override fun getViewResource(): Int {
        return R.layout.fragment_main
    }

    override fun setUp() {
        addNotes = requireView().findViewById(R.id.addNotes)
        recyclerView = requireView().findViewById(R.id.recycleview_main)
        realm = Realm.getDefaultInstance()
        noteAdapter = NoteAdapter(activity, this)
        (activity as MainActivity?)?.setOnHeadlineSelectedListener(this)
        navController = Navigation.findNavController(requireView())
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        addNotes.setOnClickListener {
            navController!!.navigate(R.id.action_nav_main_to_nav_addnote)
        }
        getAllTodo()
        addNotes.setOnLongClickListener {
            realm.beginTransaction()
            realm.deleteAll()
            realm.commitTransaction()
            getAllTodo()
            return@setOnLongClickListener true
        }


    }
    private fun getAllTodo() {
        notesList = ArrayList()
        val results: RealmResults<Note> = realm.where<Note>(
            Note::class.java
        ).findAll()
        noteAdapter.setList(results)

        recyclerView.adapter = noteAdapter

    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


    override fun oItemClickNote(note: Note) {
        var bundle = Bundle()
        bundle.putParcelable("Key", note)
        navController!!.navigate(R.id.action_nav_main_to_nav_detailnote, bundle)

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
//
//    private val mActionmodeCallBack: ActionMode.Callback =
//        object : ActionMode.Callback {
//            override fun onCreateActionMode(
//                mode: ActionMode,
//                menu: Menu
//            ): Boolean {
//                (activity as MainActivity).getSupportActionBar()!!.hide()
//                val constraintLayout = LayoutInflater
//                    .from(activity)
//                    .inflate(R.layout.custom_action_mode_search, null) as ConstraintLayout
//                mode.customView = constraintLayout
//                mode.customView = constraintLayout
//                edtSearch =
//                    constraintLayout.findViewById<EditText>(R.id.edtSearch) as AppCompatEditText?
//                tvClearSearch = constraintLayout.findViewById(R.id.tvClearSearchHome)
//              tvClearSearch.setOnClickListener {  }
//                tvCancelSearch = constraintLayout.findViewById(R.id.tvCancelSearch)
//                tvCancelSearch.setOnClickListener(View.OnClickListener { view: View? ->
//                    actionMode!!.finish()
//                    actionMode = null
//                    tvPathMobileDevice.setVisibility(View.VISIBLE)
//                })
//                edtSearch.addTextChangedListener(object : TextWatcher {
//                    override fun beforeTextChanged(
//                        charSequence: CharSequence,
//                        i: Int,
//                        i1: Int,
//                        i2: Int
//                    ) {
//                    }
//
//                    override fun onTextChanged(
//                        charSequence: CharSequence,
//                        i: Int,
//                        i1: Int,
//                        i2: Int
//                    ) {
//                        if ("" != edtSearch.getText().toString().trim({ it <= ' ' })) {
//                            tvClearSearch.visibility = View.VISIBLE
//                        } else {
//                            tvClearSearch.visibility = View.GONE
//                        }
//                    }
//
//                    override fun afterTextChanged(editable: Editable) {
//                        recyclerView.setVisibility(View.VISIBLE)
//                        adapter.filterList(listFilter)
//                        mTextSearch = editable.toString()
//                        if (mTextSearch == "") {
//                            return
//                        }
//                        filter(editable.toString())
//                    }
//                })
//
//                return true
//            }
//
//            override fun onPrepareActionMode(
//                mode: ActionMode?,
//                menu: Menu?
//            ): Boolean {
//                return false
//            }
//
//            override fun onActionItemClicked(
//                mode: ActionMode?,
//                item: MenuItem?
//            ): Boolean {
//                return false
//            }
//
//            override fun onDestroyActionMode(mode: ActionMode?) {
//                actionMode = null
//                getSupportActionBar()!!.show()
//                adapter.clearList()
//                loadFileData()
//            }
//        }

}
