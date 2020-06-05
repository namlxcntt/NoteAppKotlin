package com.lxn.noteapp.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lxn.noteapp.model.Note
import com.lxn.noteappmvvm.R
import io.realm.RealmResults


class NoteAdapter(
    val context: Context?,
    private val onItemClick: OnItemClickNote
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var noteList: RealmResults<Note>
    private var mType = 2

    companion object {
        private val llSize: Int = 1
        private val grSize: Int = 2;
    }

    fun setmType(type: Int) {
        this.mType = type
    }

    override fun getItemViewType(position: Int): Int {
        if (mType == 1) {
            return llSize
        } else {
            return grSize
        }
        return super.getItemViewType(position)
    }


    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setList(list: RealmResults<Note>) {
        noteList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            llSize -> {
                var view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_linear_rv, parent, false)
                return ViewHolder(view)
            }
            else -> {
                var view2: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_staggered_rv, parent, false)
                return ViewHolder2(view2)
            }
        }

    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_title = itemView.findViewById<TextView>(R.id.stag_Title)
        var desc = itemView.findViewById<TextView>(R.id.stag_desc)
        var id = itemView.findViewById<TextView>(R.id.stag_id)

        fun onBind(note: Note, action: OnItemClickNote) {
            tv_title.text = note.title
            desc.text = note.descripstion
            id.text = note.id.toString()
            itemView.setOnClickListener {
                action.oItemClickNote(note)
            }


        }

    }

    class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_title = itemView.findViewById<TextView>(R.id.stag_Title)
        var desc = itemView.findViewById<TextView>(R.id.stag_desc)
        var id = itemView.findViewById<TextView>(R.id.stag_id)

        fun onBind(note: Note, action: OnItemClickNote) {
            tv_title.text = note.title

            desc.text = note.descripstion
            id.text = note.id.toString()
            itemView.setOnClickListener {
                action.oItemClickNote(note)
            }


        }

    }

    interface OnItemClickNote {
        fun oItemClickNote(note: Note)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            llSize -> {
                var viewHolder: ViewHolder = holder as ViewHolder
                viewHolder.onBind(noteList.get(position)!!, onItemClick)
            }
            grSize -> {
                var viewHolder: ViewHolder2 = holder as ViewHolder2
                viewHolder.onBind(noteList.get(position)!!, onItemClick)
            }
        }
    }


}

