package com.lxn.noteapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Layout
import android.util.Log
import android.util.LogPrinter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.lxn.noteapp.model.Note
import com.lxn.noteappmvvm.R
import com.lxn.noteappmvvm.ui.main.DialogDelete
import io.realm.RealmResults


class NoteAdapter(
    val context: Context?,
    val onItemClick: OnItemClickNote
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var noteList: MutableList<Note> = ArrayList()
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

    fun setList(list: List<Note>) {
        noteList = list as MutableList<Note>
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        noteList.removeAt(position)
        notifyItemRemoved(position)
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        DialogDelete.CallbackDelete {
        var tv_title = itemView.findViewById<TextView>(R.id.stag_Title)
        var desc = itemView.findViewById<TextView>(R.id.stag_desc)
        var id = itemView.findViewById<TextView>(R.id.stag_id)
        var color_bg = itemView.findViewById<ImageView>(R.id.color_bg)
        var cardView = itemView.findViewById<CardView>(R.id.cardview_LL)

        fun onBind(note: Note, action: OnItemClickNote) {
            tv_title.text = note.title
            desc.text = note.descripstion
            id.text = note.id.toString()
            itemView.setOnClickListener {
                action.oItemClickNote(note)
            }
            when (note.color) {
                1 -> color_bg.setImageResource(R.color.color_circle1)
                2 -> color_bg.setImageResource(R.color.color_circle2)
                3 -> color_bg.setImageResource(R.color.color_circle3)
                4 -> color_bg.setImageResource(R.color.color_circle4)
                5 -> color_bg.setImageResource(R.color.color_circle5)
            }
            color_bg.animation =
                AnimationUtils.loadAnimation(itemView.context, R.anim.fade_transition_animation)
            cardView.animation =
                AnimationUtils.loadAnimation(itemView.context, R.anim.fade_scale_transition)
            itemView.setOnClickListener {
                action.oItemClickNote(note)
            }
            itemView.setOnLongClickListener(View.OnLongClickListener {
                var dialogDelete = DialogDelete.newInstance(note, this)
                var activity: FragmentActivity = it.context as FragmentActivity
                var fm: FragmentManager = activity.supportFragmentManager
                dialogDelete.show(fm, "show")
                return@OnLongClickListener true
            })
            onItemClick.onItemNotes(noteList.size)


        }
        override fun callback() {
            onItemClick.callBackDelete()
        }
    }
    inner class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView),
        DialogDelete.CallbackDelete {

        var tv_title = itemView.findViewById<TextView>(R.id.stag_Title)
        var desc = itemView.findViewById<TextView>(R.id.stag_desc)
        var id = itemView.findViewById<TextView>(R.id.stag_id)
        var color_bg = itemView.findViewById<ImageView>(R.id.color_bg)
        var cardView = itemView.findViewById<CardView>(R.id.cardview_staggered)

        fun onBind(note: Note, action: OnItemClickNote) {
            color_bg.animation =
                AnimationUtils.loadAnimation(itemView.context, R.anim.fade_transition_animation)
            cardView.animation =
                AnimationUtils.loadAnimation(itemView.context, R.anim.fade_scale_transition)
            tv_title.text = note.title
            desc.text = note.descripstion
            id.text = note.id.toString()
            when (note.color) {
                1 -> color_bg.setImageResource(R.color.color_circle1)
                2 -> color_bg.setImageResource(R.color.color_circle2)
                3 -> color_bg.setImageResource(R.color.color_circle3)
                4 -> color_bg.setImageResource(R.color.color_circle4)
                5 -> color_bg.setImageResource(R.color.color_circle5)
            }
            itemView.setOnClickListener {
                action.oItemClickNote(note)
            }
            itemView.setOnLongClickListener(View.OnLongClickListener {
                var dialogDelete = DialogDelete.newInstance(note, this)
                var activity: FragmentActivity = it.context as FragmentActivity
                var fm: FragmentManager = activity.supportFragmentManager
                dialogDelete.show(fm, "show")
                return@OnLongClickListener true
            })
            onItemClick.onItemNotes(noteList.size)

        }

        override fun callback() {
            onItemClick.callBackDelete()
        }
    }
    interface OnItemClickNote {
        fun oItemClickNote(note: Note)
        fun callBackDelete()
        fun onItemNotes(notes: Int)
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

