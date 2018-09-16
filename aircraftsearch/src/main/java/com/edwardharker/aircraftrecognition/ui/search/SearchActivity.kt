package com.edwardharker.aircraftrecognition.ui.search

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.EditText
import com.edwardharker.aircraftrecognition.ui.Navigator
import com.edwardharker.aircraftrecognition.ui.navigator
import com.edwardharker.aircraftrecognition.ui.aircraftdetail.launchAircraftDetailActivity
import com.edwardharker.aircraftrecognition.ui.bind
import com.edwardharker.aircraftrecognition.ui.feedback.launchFeedbackDialog
import com.edwardharker.aircraftsearch.R
import com.jakewharton.rxbinding.widget.RxTextView
import redux.Action
import redux.observe
import redux.dispatch
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

fun Navigator.launchSearchActivity() {
    val intent = Intent(activity, SearchActivity::class.java)
    activity.startActivity(
        intent,
        ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
    )
}

class SearchActivity : AppCompatActivity() {
    private val toolbar by bind<Toolbar>(R.id.toolbar)
    private val searchEditText by bind<EditText>(R.id.search_box)
    private val searchResults by bind<RecyclerView>(R.id.search_results)

    private val searchAdapter = SearchAdapter(
        aircraftClickListener = {
            navigator.launchAircraftDetailActivity(it.id)
        },
        feedbackClickListener = {
            navigator.launchFeedbackDialog()
        }
    )

    private val searchStore = searchStore()
    private val disposables = CompositeSubscription()
    private var eventsDisposable: Subscription? = null

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

        searchResults.adapter = searchAdapter

        val events: Observable<Action> = RxTextView.afterTextChangeEvents(searchEditText)
            .debounce(200, TimeUnit.MILLISECONDS)
            .filter { it.toString().isNotBlank() }
            .observeOn(AndroidSchedulers.mainThread())
            .map { searchEditText.text.toString() }
            .compose(searchUseCase()::searchAircraftByName)

        eventsDisposable = searchStore.dispatch(events)
    }

    override fun onStart() {
        super.onStart()
        disposables.add(searchStore.observe().subscribe {
            searchAdapter.bindSearchResults(it.searchResults)
        })
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        eventsDisposable?.unsubscribe()
    }
}
