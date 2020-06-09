package com.lxn.noteappmvvm.ui.detailnote

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lxn.noteapp.`interface`.NoteModel
import com.lxn.noteapp.model.Note
import com.lxn.noteappmvvm.MainActivity
import com.lxn.noteappmvvm.R
import com.lxn.noteappmvvm.base.BaseFragment
import com.lxn.noteappmvvm.base.LinedEditText
import com.vicpin.krealmextensions.save
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_detail_note.*


/**
 * A simple [Fragment] subclass.
 */
class DetailNoteFragment : BaseFragment(), View.OnClickListener {
    private lateinit var note: Note
    private lateinit var navController: NavController
    private lateinit var realm: Realm
    private lateinit var constrainsTop: ConstraintLayout
    private lateinit var constrainChangeText: ConstraintLayout
    private lateinit var et_description: LinedEditText
    private lateinit var constrainDetail: ConstraintLayout
    private lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    private lateinit var toolbar: Toolbar
    private lateinit var fab: FloatingActionButton
    private lateinit var fabDone: FloatingActionButton

    override fun getViewResource(): Int {
        return R.layout.fragment_detail_note
    }


    @SuppressLint("ResourceAsColor")
    override fun setUp() {
        if (requireArguments().getParcelable<Note>("Key") != null) {
            note = requireArguments().getParcelable<Note>("Key")!!
        }
        realm = Realm.getDefaultInstance()
        toolbar = requireView().findViewById(R.id.toolbar_detail)
        navController = Navigation.findNavController(requireView())
        et_description = requireView().findViewById(R.id.et_description)
        collapsingToolbarLayout = requireView().findViewById(R.id.coordinatorLayout)
        fab = requireView().findViewById(R.id.fab_change)
        fabDone = requireView().findViewById(R.id.fab_done)
        (activity as MainActivity)!!.bottomBar.visibility = View.GONE
        coordinatorLayout.title = note.title.toString().trim()
        et_description.setText(note.descripstion.toString().trim())
        tv_date.text = "Date:  ${note.date!!.substring(0, note.date!!.lastIndexOf("G"))}"
        toolbar.setOnClickListener(this)
        fab.setOnClickListener(this)
        fabDone.setOnClickListener(this)
    }


    override fun onDetach() {
        (activity as MainActivity)!!.bottomBar.visibility = View.VISIBLE
        super.onDetach()

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fab_change -> {
                et_description.isEnabled = et_description.isEnabled == false
                if (fab.isVisible) {
                    fabDone.visibility = View.VISIBLE
                    fab.visibility = View.INVISIBLE
                } else {
                    fabDone.visibility = View.INVISIBLE
                    fab.visibility = View.VISIBLE
                }
            }
            R.id.fab_done -> {
                Toast.makeText(context, "click cl;ick", Toast.LENGTH_SHORT).show()
                et_description.isEnabled = false
                Note(
                    note.id
                    , note.title
                    , et_description.text.toString().trim(),
                    note.date,
                    note.complete,
                    note.color
                ).save()
                navController.navigate(R.id.action_nav_detailnote_to_nav_main)
            }
            R.id.toolbar_detail -> activity?.onBackPressed()


        }
    }
}
