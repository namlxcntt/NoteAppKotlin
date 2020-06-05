package com.lxn.noteappmvvm.ui.addnote

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.lxn.noteapp.`interface`.NoteModel
import com.lxn.noteapp.model.Note
import com.lxn.noteappmvvm.R
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_add_note.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class AddNoteFragment : Fragment(), View.OnClickListener {

    private lateinit var titleET: EditText
    private lateinit var descET: EditText
    private lateinit var saveBtn: Button
    private lateinit var realm: Realm
    private lateinit var helper: NoteModel
    private lateinit var timeStart: TextView
    private lateinit var timeEnd: TextView
    private lateinit var buttonShowDayStart: Button
    private lateinit var buttonShowDayEnd: Button
    private var timeStartString: String = ""
    private var timeEndString: String = ""
    private val cal = Calendar.getInstance()
    private val year = cal.get(Calendar.YEAR)
    private val month = cal.get(Calendar.MONTH)
    private val day = cal.get(Calendar.DAY_OF_MONTH)
    private lateinit var textInputTitle: TextInputLayout
    private lateinit var textInputDescription: TextInputLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        realm = Realm.getDefaultInstance()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        //init views
        initView(view)

        saveBtn.setOnClickListener {
            saveData()
        }
    }

    private fun initView(view: View) {
        titleET = view.findViewById(R.id.edt_title)
        descET = view.findViewById(R.id.edt_description)
        saveBtn = view.findViewById(R.id.saveBtn)
        timeStart = view.findViewById(R.id.tv_start_time)
        timeEnd = view.findViewById(R.id.tv_end_time)
        buttonShowDayStart = view.findViewById(R.id.button_click_time)
        buttonShowDayEnd = view.findViewById(R.id.button_click_time2)
        buttonShowDayStart.setOnClickListener(this)
        buttonShowDayEnd.setOnClickListener(this)
        textInputTitle = view.findViewById(R.id.textInputLayout_title)
        textInputDescription = view.findViewById(R.id.textInputLayout_description)
        button_back.setOnClickListener(this)

    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun saveData() {
        try {
            helper = NoteModel()
            val task = Note()
            if (titleET.text.toString().isEmpty()
                || descET.text.toString().isEmpty()
                || timeStart.text.toString().isEmpty()
                || timeEnd.text.toString().isEmpty()
            ) {

                if (titleET.text.toString().isEmpty()) {
                    titleET.requestFocus()
//                    textInputTitle.error = "Please input title Note"
                    view!!.snack("Please input title Note")
                } else if (descET.text.toString().isEmpty()) {
//                    textInputDescription.error = "Please input description Note"
                    view!!.snack("Please input description Note")
                } else if (timeStart.text.toString().isEmpty()) {
                    view!!.snack("Please Input timeStart")
                } else if (timeEnd.text.toString().isEmpty()) {
                    view!!.snack("Please input timeEnd")
                }
            } else {
                task.title = titleET.text.toString()
                task.descripstion = descET.text.toString()
                task.date = timeStart.text.toString() + "*" + timeEnd.text.toString()
                task.complete = false;
                //saving to Database
                helper.addNote(realm, task)
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
//                fragmentManager?.beginTransaction()!!.replace(R.id.frame_layout, MainFragment())
//                    .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right)
//                    .addToBackStack(null)
//                    .commit()
            }


        } catch (e: Exception) {

            Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()

        }

    }

    fun View.snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(this, message, duration).show()
    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.button_click_time -> {
                val datePickerDialog: DatePickerDialog =
                    DatePickerDialog(
                        context!!,
                        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                            timeStart.setText("" + year + "/" + month + "/" + dayOfMonth)
                        },
                        year,
                        month,
                        day
                    )
                datePickerDialog.show()
            }
            R.id.button_click_time2 -> {
                val datePickerDialog: DatePickerDialog =
                    DatePickerDialog(
                        context!!,
                        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                            timeEnd.setText("" + year + "/" + month + "/" + dayOfMonth)
                        },
                        year,
                        month,
                        day
                    )
                datePickerDialog.show()
            }
            R.id.button_back -> activity!!.onBackPressed()
        }
    }

}
