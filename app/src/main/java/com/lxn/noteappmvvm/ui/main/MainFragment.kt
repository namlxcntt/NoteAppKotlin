package com.lxn.noteappmvvm.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
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
    lateinit var addNotes: FloatingActionButton
    lateinit var recyclerView: RecyclerView
    private lateinit var realm: Realm
    private lateinit var noteAdapter: NoteAdapter
    private var notesList = ArrayList<Note>()
    val PREFS_FILENAME = "note-App"
    private var actionMode: ActionMode? = null
    private var tvCancelSearch: TextView? = null
    private var tvClearSearch: TextView? = null
    var mType = 1
    private var edtSearch: EditText? = null
    private var mTextSearch = ""


    override fun getViewResource(): Int {
        return R.layout.fragment_main
    }

    override fun setUp() {
        addNotes = requireView().findViewById(R.id.addNotes)
        recyclerView = requireView().findViewById(R.id.recycleview_main)
        realm = Realm.getDefaultInstance()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        noteAdapter = NoteAdapter(activity, this)
        (activity as MainActivity?)?.setOnHeadlineSelectedListener(this)


        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        addNotes.setOnClickListener {
//            fragmentManager!!.beginTransaction()
//                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
//                .replace(R.id.frame_layout, AddNoteFragment())
//                .addToBackStack(null)
//                .commit()
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

    override fun setUpObservable() {
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
        var detailNoteFragment: DetailNoteFragment = DetailNoteFragment()
        detailNoteFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
            .replace(android.R.id.content, detailNoteFragment)
            .addToBackStack(null)
            .commit()

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
