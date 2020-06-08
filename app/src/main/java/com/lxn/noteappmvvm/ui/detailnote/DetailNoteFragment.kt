package com.lxn.noteappmvvm.ui.detailnote

import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.lxn.noteapp.`interface`.NoteModel
import com.lxn.noteapp.model.Note
import com.lxn.noteappmvvm.MainActivity
import com.lxn.noteappmvvm.R
import com.lxn.noteappmvvm.base.BaseFragment
import com.lxn.noteappmvvm.base.LinedEditText
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_detail_note.*


/**
 * A simple [Fragment] subclass.
 */
class DetailNoteFragment : BaseFragment(), View.OnClickListener {
    private lateinit var note: Note
    private lateinit var navController: NavController
    private lateinit var realm: Realm
    private lateinit var helper: NoteModel
    private lateinit var constrainsTop: ConstraintLayout
    private lateinit var constrainChangeText: ConstraintLayout
    private lateinit var et_description: LinedEditText


    override fun getViewResource(): Int {
        return R.layout.fragment_detail_note
    }

    override fun setUp() {
        realm = Realm.getDefaultInstance()

        navController = Navigation.findNavController(requireView())
        constrainsTop = requireView().findViewById(R.id.constrain_top)
        constrainChangeText = requireView().findViewById(R.id.constrain_edittext)
        et_description = requireView().findViewById(R.id.et_description)

        (activity as MainActivity)!!.bottomBar.visibility = View.GONE

        if (requireArguments().getParcelable<Note>("Key") != null) {
            note = requireArguments().getParcelable<Note>("Key")!!
        }
        tv_title.text = note.title.toString().trim()
        et_description.setText(note.descripstion.toString().trim())
        tv_date.text = "Date:  ${note.date!!.substring(0, note.date!!.lastIndexOf("G"))}"


        button_back.setOnClickListener(this)
        button_edit.setOnClickListener(this)
        btn_done.setOnClickListener(this)
        tv_cancel.setOnClickListener(this)
    }


    override fun onDetach() {
        (activity as MainActivity)!!.bottomBar.visibility = View.VISIBLE
        super.onDetach()

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_edit -> {
                Toast.makeText(context, "Click Click", Toast.LENGTH_SHORT).show()
                et_description.isEnabled = et_description.isEnabled == false
                if (constrainsTop.isVisible) {
                    constrainsTop.visibility = View.INVISIBLE
                    constrainChangeText.visibility = View.VISIBLE
                } else {
                    constrainsTop.visibility = View.VISIBLE
                    constrainChangeText.visibility = View.INVISIBLE
                }
            }
            R.id.button_back -> activity?.onBackPressed()
            R.id.tv_cancel -> {
                constrainsTop.visibility = View.VISIBLE
                constrainChangeText.visibility = View.INVISIBLE
            }
            R.id.btn_done -> {
                helper = NoteModel()
                note.descripstion = et_description.text.toString()
                helper.updateNote(realm, note)
                constrainsTop.visibility = View.VISIBLE
                constrainChangeText.visibility = View.INVISIBLE


            }

        }
    }
}
