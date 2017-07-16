package com.edwardharker.aircraftrecognition.ui.search

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.EditText
import com.edwardharker.aircraftrecognition.repository.aircraftRepository
import com.edwardharker.aircraftrecognition.search.*
import com.edwardharker.aircraftrecognition.ui.ActivityLauncher
import com.edwardharker.aircraftrecognition.ui.bind
import com.edwardharker.aircraftsearch.R
import com.jakewharton.rxbinding.widget.RxTextView
import rx.Observable
import rx.subscriptions.CompositeSubscription

fun ActivityLauncher.launchSearchActivity() {
    val intent = Intent(activity, SearchActivity::class.java)
    activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
}

class SearchActivity : AppCompatActivity() {

    private val toolbar by bind<Toolbar>(R.id.toolbar)
    private val searchEditText by bind<EditText>(R.id.search_box)

    private val searchStore = searchStore()
    private val disposables = CompositeSubscription()

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

    override fun onStart() {
        super.onStart()

        val events: Observable<SearchAction> = RxTextView.afterTextChangeEvents(searchEditText)
                .map { QueryChangedAction(searchEditText.text.toString()) }

        disposables.add(searchStore.observe(events).subscribe {
            Log.d("---->", "${it.searchResults}")
        })
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }
}
