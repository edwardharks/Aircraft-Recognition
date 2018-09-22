package com.edwardharker.aircraftrecognition.stetho

import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.Stetho.newInitializerBuilder

//import com.uphyca.stetho_realm.RealmInspectorModulesProvider

fun initialiseStetho(context: Context) {
    Stetho.initialize(
        newInitializerBuilder(context)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
            //.enableWebKitInspector(RealmInspectorModulesProvider.builder(context).build())
            .build()
    )
}
