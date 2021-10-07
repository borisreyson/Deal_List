package com.misit.deallist

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyAplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("near.deal")
            .schemaVersion(0)
            .build()
        Realm.setDefaultConfiguration(configuration)
    }
}