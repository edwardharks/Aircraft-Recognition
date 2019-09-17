package com.edwardharker.aircraftrecognition.ui.search

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.edwardharker.aircraftrecognition.ui.Navigator
import com.edwardharker.aircraftrecognition.ui.aircraftdetail.launchAircraftDetailActivity
import com.edwardharker.aircraftrecognition.ui.bind
import com.edwardharker.aircraftrecognition.ui.feedback.launchFeedbackDialog
import com.edwardharker.aircraftrecognition.ui.navigator
import com.edwardharker.aircraftrecognition.ui.showKeyboard
import com.edwardharker.aircraftsearch.R
import com.jakewharton.rxbinding.widget.RxTextView
import redux.Action
import redux.dispatch
import redux.observe
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

fun Navigator.launchSearchActivity() {
    launch(
        intent = Intent(activity, SearchActivity::class.java),
        options = ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
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

        searchEditText.showKeyboard()

        searchResults.adapter = searchAdapter

        val events: Observable<Action> = RxTextView.afterTextChangeEvents(searchEditText)
            .debounce(SEARCH_DELAY, TimeUnit.MILLISECONDS)
            .filter { it.toString().isNotBlank() }
            .observeOn(AndroidSchedulers.mainThread())
            .map { searchEditText.text.toString() }
            .compose(searchUseCase()::searchAircraftByName)

        eventsDisposable = searchStore.dispatch(events)
    }

    override fun onStart() {
        super.onStart()
        disposables.add(searchStore.observe().subscribe {
            searchAdapter.submitList(it.searchResults)
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

    private companion object {
        private const val SEARCH_DELAY = 200L
    }
}
