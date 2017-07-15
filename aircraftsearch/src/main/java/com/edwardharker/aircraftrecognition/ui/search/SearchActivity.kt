package com.edwardharker.aircraftrecognition.ui.search

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.edwardharker.aircraftrecognition.ui.ActivityLauncher
import com.edwardharker.aircraftrecognition.ui.bind
import com.edwardharker.aircraftsearch.R

fun ActivityLauncher.launchSearchActivity() {
    val intent = Intent(activity, SearchActivity::class.java)
    activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
}

class SearchActivity : AppCompatActivity() {

    private val toolbar by bind<Toolbar>(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ""
        toolbar.apply {
            setNavigationOnClickListener { onBackPressed() }
            setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        }
    }
}
