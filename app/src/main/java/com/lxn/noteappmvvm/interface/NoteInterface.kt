package com.lxn.noteapp.`interface`

import com.lxn.noteapp.model.Note
import io.realm.Realm

interface NoteInterface {
    fun addNote(realm: Realm, note: Note): Boolean
    fun deleteNote(realm: Realm, id: Int): Boolean
    fun updateNote(realm: Realm, note: Note): Boolean

}