package com.lxn.noteapp.`interface`

import com.lxn.noteapp.model.Note
import io.realm.Realm
import java.lang.Exception

class NoteModel : NoteInterface {
    override fun addNote(realm: Realm, note: Note): Boolean {
        return try {
            realm.beginTransaction()

            val currentIdNum: Number? = realm.where(Note::class.java).max("id")

            val nextId: Int

            nextId = if (currentIdNum == null) {
                1
            } else {
                currentIdNum.toInt() + 1
            }
            note.id = nextId // Id Primary key
            realm.copyToRealmOrUpdate(note)
            realm.commitTransaction()

            true
        } catch (e: Exception) {
            println(e)
            false
        }
    }

    override fun deleteNote(realm: Realm, id: Int): Boolean {
        try {
            realm.beginTransaction()
            realm.where(Note::class.java).equalTo("id", id).findFirst()?.deleteFromRealm()
            realm.commitTransaction()
            return true

        } catch (e: Exception) {
            println(e)
            return false
        }
    }

    override fun updateNote(realm: Realm, note: Note): Boolean {
        TODO("Not yet implemented")

    }
}