package com.lxn.noteappmvvm.base

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("Notes.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()



        Realm.setDefaultConfiguration(configuration)


    }
}