package com.lxn.noteappmvvm.ui.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.ceryle.radiorealbutton.RadioRealButton
import co.ceryle.radiorealbutton.RadioRealButtonGroup
import com.lxn.noteapp.adapter.NoteAdapter
import com.lxn.noteapp.model.Note
import com.lxn.noteappmvvm.R
import com.lxn.noteappmvvm.base.BaseFragment
import com.vicpin.krealmextensions.queryAll
import io.realm.Realm
import io.realm.RealmResults
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : BaseFragment(), NoteAdapter.OnItemClickNote, View.OnClickListener {
    private lateinit var recycleView: RecyclerView
    private lateinit var edittext: AppCompatEditText
    private lateinit var btnBack: ImageView
    private lateinit var btnSpeechToText: ImageView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var navController: NavController
    private lateinit var tvSearchColor: TextView
    private lateinit var radioRealButton: RadioRealButtonGroup
    val list: MutableList<Note> = ArrayList()
    override fun getViewResource(): Int {
        return R.layout.fragment_search
    }

    companion object {
        var REQUEST_CODE_SPEECH: Int = 10
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun setUp() {
        navController = Navigation.findNavController(requireView())
        tvSearchColor = view!!.findViewById(R.id.tv_select_color)
        radioRealButton = view!!.findViewById(R.id.radioRealButtonGroup)

        recycleView = view!!.findViewById(R.id.recyclerView)
        edittext = view!!.findViewById(R.id.edt_search)
        btnBack = view!!.findViewById(R.id.btn_back)
        btnSpeechToText = view!!.findViewById(R.id.btn_speech_to_text)
        noteAdapter = NoteAdapter(activity, this)
        recycleView.layoutManager =
            LinearLayoutManager(context!!)
        recycleView.adapter = noteAdapter
        noteAdapter.setmType(1)
//        getAllTodo()
        searchEdittext()
        tvSearchColor.setOnClickListener(this)
        btnSpeechToText.setOnClickListener(this)
        btnBack.setOnClickListener(this)

        radioRealButton.setOnClickedButtonListener { button, position ->
            recycleView.visibility = View.VISIBLE
            when (position) {
                0 -> filterColor(1)
                1 -> filterColor(2)
                2 -> filterColor(3)
                3 -> filterColor(4)
                4 -> filterColor(5)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_SPEECH -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    var result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    edittext.setText(result.get(0).toString())
                }
            }
        }
    }

    private fun searchEdittext() {
        edittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                radioRealButton.visibility = View.INVISIBLE
                recycleView.visibility = View.VISIBLE
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    private fun filterColor(number: Int) {
        list.clear()
        for (note in Note().queryAll()) {
            if (note.color == number) {
                list.add(note)
            }
            noteAdapter.setList(list)
        }
    }

    private fun filter(text: String) {
        list.clear()
        for (note in Note().queryAll()) {
            if (note.title!!.toLowerCase().contains(text.toLowerCase())) {
                list.add(note)
            }
            noteAdapter.setList(list)
        }


    }

    override fun oItemClickNote(note: Note) {
        var bundle = Bundle()
        bundle.putParcelable("Key", note)
        navController!!.navigate(R.id.action_nav_search_to_nav_detailnote, bundle)
    }

    override fun callBackDelete() {
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_speech_to_text -> {
                var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi Speak Something")

                //
                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH)

                } catch (e: Exception) {
                    Log.d("xxxx", e.message)
                }
            }
            R.id.tv_select_color ->
                radioRealButton.visibility = View.VISIBLE
            R.id.btn_back -> activity!!.onBackPressed()

        }
    }


}