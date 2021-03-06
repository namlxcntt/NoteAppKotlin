package com.lxn.noteappmvvm.ui.addnote

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.Navigation
import co.ceryle.radiorealbutton.RadioRealButtonGroup
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.lxn.noteapp.`interface`.NoteModel
import com.lxn.noteapp.model.Note
import com.lxn.noteappmvvm.R
import com.lxn.noteappmvvm.base.BaseFragment
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_add_note.*
import java.util.*

class AddNoteFragment : BaseFragment(), View.OnClickListener {

    private lateinit var titleET: EditText
    private lateinit var descET: EditText
    private lateinit var saveBtn: Button
    private lateinit var realm: Realm
    private lateinit var helper: NoteModel
    private lateinit var radioRealButtonGroup: RadioRealButtonGroup
    private lateinit var textInputTitle: TextInputLayout
    private lateinit var textInputDescription: TextInputLayout
    private lateinit var navController: NavController
    lateinit var currentTime: String
    private var colorNote: Int = 0

    override fun getViewResource(): Int {
        return R.layout.fragment_add_note
    }

    override fun setUp() {
        navController = Navigation.findNavController(requireView())
        currentTime = Calendar.getInstance().time.toString()
        realm = Realm.getDefaultInstance()
        view?.let {
            initView(it)
        }

    }

    private fun initView(view: View) {
        titleET = view.findViewById(R.id.edt_title)
        descET = view.findViewById(R.id.edt_description)
        saveBtn = view.findViewById(R.id.saveBtn)
        saveBtn.setOnClickListener(this)
        textInputTitle = view.findViewById(R.id.textInputLayout_title)
        textInputDescription = view.findViewById(R.id.textInputLayout_description)
        button_back.setOnClickListener(this)
        radioRealButtonGroup = view.findViewById(R.id.radioRealButtonGroup)
        radioRealButtonGroup.setOnClickedButtonListener { button, position ->
            if (position == 0) {
                colorNote = 1

            } else if (position == 1) {
                colorNote = 2

            } else if (position == 2) {
                colorNote = 3

            } else if (position == 3) {
                colorNote = 4

            } else if (position == 4) {
                colorNote = 5

            }
        }

    }

    private fun saveData() {
        try {
            helper = NoteModel()
            val task = Note()
            if (titleET.text.toString().isEmpty()
                || descET.text.toString().isEmpty()
                || colorNote == null
            ) {

                if (titleET.text.toString().isEmpty()) {
                    titleET.requestFocus()
                    requireView().snack("Please input title Note")
                } else if (descET.text.toString().isEmpty()) {
                    requireView().snack("Please input description Note")
                } else if (colorNote == 0) {
                    requireView().snack("Please select color Note ( ^-^ )")
                }
            } else {

                task.title = titleET.text.toString()
                task.descripstion = descET.text.toString()
                task.date = currentTime
                task.complete = false;
                task.color = colorNote
                helper.addNote(realm, task)
                navController.navigate(R.id.action_nav_addnote_to_nav_main)


            }
        } catch (e: Exception) {
            Log.d("xxxx", e.toString())

        }

    }

    fun View.snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(this, message, duration).show()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_back -> requireActivity().onBackPressed()
            R.id.saveBtn -> saveData()


        }
    }


}
