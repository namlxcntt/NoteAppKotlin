package com.lxn.noteappmvvm.ui.detailnote

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.lxn.noteapp.model.Note
import com.lxn.noteappmvvm.R
import kotlinx.android.synthetic.main.fragment_detail_note.*
import kotlin.math.log

/**
 * A simple [Fragment] subclass.
 */
class DetailNoteFragment : Fragment() {
    private lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (requireArguments().getParcelable<Note>("Key") != null) {
            note = requireArguments().getParcelable<Note>("Key")!!
        }
        tv_title.text = note.title.toString().trim()
        tv_description.setText(note.descripstion.toString().trim())
        var time: String = note.date.toString()
        var int = time.indexOf('*')
        tv_start.text = "Time start: " + time.substring(0, int)
        tv_end.text = "Time End: " + time.substring(int + 1, time.length)

        button_back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initView(view: View) {


    }

}
