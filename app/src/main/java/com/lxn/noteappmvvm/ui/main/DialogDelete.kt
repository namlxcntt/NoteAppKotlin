package com.lxn.noteappmvvm.ui.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.lxn.noteapp.model.Note
import com.lxn.noteappmvvm.R
import com.vicpin.krealmextensions.delete
import kotlinx.android.synthetic.main.dialog_delete.*
import java.util.*

class DialogDelete : DialogFragment(), View.OnClickListener {
    private lateinit var note: Note
    companion object {
        private var callBackDelete: CallbackDelete? = null
        private lateinit var dialogDelete: DialogDelete
        fun newInstance(note: Note, callBackDelete: CallbackDelete): DialogFragment {
            this.callBackDelete = callBackDelete
            dialogDelete = DialogDelete()
            var bundle = Bundle()
            bundle.putParcelable("Key", note)
            dialogDelete.arguments = bundle
            return dialogDelete
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.dialog_delete, container, false)
        note = arguments!!.getParcelable<Note>("Key")!!
        dialog!!.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonDelete.setOnClickListener(this)
        buttonCancel.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.buttonDelete -> {
                Note().delete {
                    equalTo("id", note.id)
                }
                callBackDelete!!.callback()
                dialog!!.dismiss()


            }
            R.id.buttonCancel -> {
                dialog!!.dismiss()
            }
        }
    }

    public interface CallbackDelete {
        fun callback()
    }
}