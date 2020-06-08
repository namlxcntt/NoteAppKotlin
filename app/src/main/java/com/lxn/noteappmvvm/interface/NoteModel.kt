package com.lxn.noteapp.`interface`

import com.lxn.noteapp.model.Note
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.delete
import kotlinx.coroutines.flow.callbackFlow
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

    override fun deleteAllNote(realm: Realm): Single<Unit> {
        var check = realm.delete<Note>()
        return check?.let {
            Single.just(check)
        } ?: Single.error(NoSuchElementException())
    }

    override fun getAllNote(realm: Realm): Flowable<RealmResults<Note>> {
        return realm.where<Note>(
            Note::class.java
        ).findAllAsync().asFlowable()
    }

    override fun updateNote(realm: Realm, note: Note): Boolean {
        realm.beginTransaction()
        realm.insertOrUpdate(note)
        realm.commitTransaction()
        return true
    }
}