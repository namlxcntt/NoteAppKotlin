package com.lxn.noteapp.model

import android.os.Parcelable
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Note(

    @PrimaryKey
    var id: Int? = null,
    var title: String? = null,
    var descripstion: String? = null,
    var date: String? = null,
    var complete: Boolean = false,
    var color: Int? = null
) : RealmObject(), Parcelable {

}

