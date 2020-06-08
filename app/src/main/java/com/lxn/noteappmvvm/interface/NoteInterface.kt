package com.lxn.noteapp.`interface`

import com.lxn.noteapp.model.Note
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmResults

interface NoteInterface {
    fun addNote(realm: Realm, note: Note): Boolean
    fun deleteNote(realm: Realm, id: Int): Boolean
    fun updateNote(realm: Realm, note: Note): Boolean
    fun deleteAllNote(realm: Realm): Single<Unit>
    fun getAllNote(realm: Realm): Flowable<RealmResults<Note>>

}