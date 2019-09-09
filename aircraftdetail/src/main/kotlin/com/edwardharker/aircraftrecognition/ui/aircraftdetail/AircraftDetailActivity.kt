package com.edwardharker.aircraftrecognition.ui.aircraftdetail

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color.argb
import android.graphics.Color.blue
import android.graphics.Color.green
import android.graphics.Color.red
import android.graphics.Typeface.BOLD
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.style.StyleSpan
import android.transition.Transition
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.ALPHA
import android.view.View.TRANSLATION_Y
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.edwardharker.aircraftrecognition.aircraftdetail.AircraftDetailView
import com.edwardharker.aircraftrecognition.aircraftdetail.AircraftDetailViewModel
import com.edwardharker.aircraftrecognition.aircraftdetail.R
import com.edwardharker.aircraftrecognition.analytics.Events.aircraftDetailEvent
import com.edwardharker.aircraftrecognition.analytics.Events.youtubeVideoClickEvent
import com.edwardharker.aircraftrecognition.analytics.aircraftDetailScreen
import com.edwardharker.aircraftrecognition.analytics.eventAnalytics
import com.edwardharker.aircraftrecognition.extension.postDelayed
import com.edwardharker.aircraftrecognition.perf.TracerFactory.aircraftDetailActivityContentLoad
import com.edwardharker.aircraftrecognition.ui.AspectRatioImageView
import com.edwardharker.aircraftrecognition.ui.Navigator
import com.edwardharker.aircraftrecognition.ui.Navigator.Companion.STARTED_WITH_TRANSITION_EXTRA
import com.edwardharker.aircraftrecognition.ui.TransitionListenerAdapter
import com.edwardharker.aircraftrecognition.ui.bind
import com.edwardharker.aircraftrecognition.ui.dpToPixels
import com.edwardharker.aircraftrecognition.ui.feedback.launchFeedbackDialog
import com.edwardharker.aircraftrecognition.ui.loadAircraftImage
import com.edwardharker.aircraftrecognition.ui.navigator
import com.edwardharker.aircraftrecognition.ui.pixelsToDp
import com.edwardharker.aircraftrecognition.youtube.youtubeStandalonePlayerHelper
import java.lang.Math.min

private const val AIRCRAFT_ID_EXTRA = "aircraftId"

fun Navigator.launchAircraftDetailActivity(
    aircraftId: String,
    aircraftImage: View,
    background: View,
    aircraftName: View
) {
    launch(
        intent = Intent(activity, AircraftDetailActivity::class.java).apply {
            putExtra(AIRCRAFT_ID_EXTRA, aircraftId)
        },
        options = ActivityOptions.makeSceneTransitionAnimation(
            activity,
            android.util.Pair(
                aircraftImage,
                activity.getString(R.string.transition_aircraft_image)
            ),
            android.util.Pair(
                background,
                activity.getString(R.string.transition_aircraft_detail_background)
            ),
            android.util.Pair(aircraftName, activity.getString(R.string.transition_aircraft_name))
        ).toBundle()
    )
}

fun Navigator.launchAircraftDetailActivity(aircraftId: String) {
    launch(
        intent = Intent(activity, AircraftDetailActivity::class.java).apply {
            putExtra(AIRCRAFT_ID_EXTRA, aircraftId)
        }
    )
}

class AircraftDetailActivity : AppCompatActivity(), AircraftDetailView {
    private val aircraftImage by bind<AspectRatioImageView>(R.id.aircraft_image)
    private val aircraftImageContainer by bind<View>(R.id.aircraft_image_container)
    private val aircraftName by bind<TextView>(R.id.aircraft_name)
    private val aircraftDescription by bind<TextView>(R.id.aircraft_description)
    private val aircraftMetaDataContainer by bind<ViewGroup>(R.id.aircraft_meta_data_container)
    private val toolbar by bind<Toolbar>(R.id.toolbar)
    private val scrollView by bind<NestedScrollView>(R.id.scroll_view)
    private val photoCarouselButton by bind<View>(R.id.photo_carousel_button)
    private val similarAircraftRail by bind<SimilarAircraftView>(R.id.similar_aircraft_rail)
    private val aircraftDetailsContainer by bind<View>(R.id.aircraft_details_container)

    private val transitionSlidingViews by lazy {
        listOf(
            aircraftDescription,
            aircraftMetaDataContainer,
            similarAircraftRail
        )
    }

    private val transitionFadingViews by lazy {
        listOf(
            aircraftMetaDataContainer,
            aircraftDescription,
            aircraftMetaDataContainer
        )
    }

    private val initialInvisibleViews by lazy {
        listOf(
            toolbar,
            aircraftDescription,
            aircraftMetaDataContainer,
            photoCarouselButton,
            similarAircraftRail
        )
    }

    private val aircraftId: String by lazy {
        when {
            intent.hasExtra(AIRCRAFT_ID_EXTRA) -> intent.getStringExtra(AIRCRAFT_ID_EXTRA)
            intent.data != null -> intent.data!!.lastPathSegment
            else -> {
                finish()
                ""
            }
        }
    }

    private val startedWithTransition by lazy {
        intent.getBooleanExtra(STARTED_WITH_TRANSITION_EXTRA, false)
    }
    private val isFromDeepLink by lazy {
        intent.data != null
    }
    private val detailsBackgroundColour by lazy {
        ContextCompat.getColor(this@AircraftDetailActivity, R.color.windowBackground)
    }
    private var animateOnBackPressed = true

    private val presenter = aircraftDetailPresenter()
    private val youtubeStandalonePlayerHelper = youtubeStandalonePlayerHelper()
    private val eventAnalytics = eventAnalytics()
    private val aircraftDetailActivityTracer = aircraftDetailActivityContentLoad()

    override fun onCreate(savedInstanceState: Bundle?) {
        aircraftDetailActivityTracer.start()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aircraft_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(!isFromDeepLink)
        title = ""
        toolbar.apply {
            setNavigationOnClickListener { onBackPressed() }
            if (!isFromDeepLink) {
                setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            }
        }

        supportPostponeEnterTransition()
        Handler().postDelayed(TRANSITION_TIMEOUT) {
            supportStartPostponedEnterTransition()
        }
        animateOnBackPressed = savedInstanceState == null

        if (savedInstanceState == null && startedWithTransition) {
            initialInvisibleViews.forEach { it.alpha = 0f }
            aircraftDetailsContainer.background = null
            window.sharedElementEnterTransition.addListener(EnterTransitionListener())
        }

        scrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, y: Int, _: Int, _: Int ->
            onScrollChanged(y)
        }

        scrollView.scrollTo(0, 0)
        onScrollChanged(0)

        aircraftImageContainer.setOnClickListener {
            navigateToPhotoCarousel()
        }

        similarAircraftRail.aircraftId = aircraftId
    }

    override fun onStart() {
        super.onStart()
        presenter.startPresenting(this, aircraftId)
        eventAnalytics.logScreenView(aircraftDetailScreen())
        eventAnalytics.logEvent(aircraftDetailEvent(aircraftId))
    }

    override fun onStop() {
        super.onStop()
        presenter.stopPresenting()
    }

    override fun onBackPressed() {
        if (scrollView.scrollY > 0 || !animateOnBackPressed) {
            finish()
        } else {
            supportFinishAfterTransition()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_aircraft_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_feedback) {
            navigator.launchFeedbackDialog()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showAircraft(aircraftDetailViewModel: AircraftDetailViewModel) {
        val aircraft = aircraftDetailViewModel.aircraft

        aircraftImage.loadAircraftImage(aircraft) {
            aircraftImage.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    supportStartPostponedEnterTransition()
                    aircraftImage.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }

        val formatAircraftName = String.format("%s %s", aircraft.manufacturer, aircraft.name)
        aircraftName.text = formatAircraftName
        title = formatAircraftName
        aircraftDescription.text = aircraft.longDescription

        aircraftMetaDataContainer.removeAllViews()
        aircraftDetailViewModel.featuredVideoId?.let { addWatchOnYoutubeItem(it) }
        aircraft.metaData.forEach { addAircraftMetaDataItem(it.key, it.value) }
        addAircraftMetaDataItem(getString(R.string.attribution), aircraft.attribution) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(aircraft.attributionUrl)))
        }

        aircraftDetailActivityTracer.stop()
    }

    private fun addAircraftMetaDataItem(
        label: String,
        value: String,
        onClick: (() -> Unit)? = null
    ) {
        val metaDataView = LayoutInflater.from(this).inflate(
            R.layout.view_meta_data_item,
            aircraftMetaDataContainer,
            false
        ) as TextView
        val spannable = SpannableString(getString(R.string.meta_data_item, label, value))
        spannable.setSpan(StyleSpan(BOLD), spannable.length - value.length, spannable.length, 0)
        metaDataView.text = spannable
        onClick?.let { _ -> metaDataView.setOnClickListener { onClick.invoke() } }
        aircraftMetaDataContainer.addView(metaDataView)
        addDivider()
    }

    private fun addWatchOnYoutubeItem(videoId: String) {
        val videoView = LayoutInflater.from(this)
            .inflate(R.layout.view_aircraft_featured_video, aircraftMetaDataContainer, false)

        videoView.setOnClickListener {
            youtubeStandalonePlayerHelper.launchYoutubeStandalonePlayer(this, videoId)
            eventAnalytics.logEvent(
                youtubeVideoClickEvent(
                    videoId = videoId,
                    aircraftId = aircraftId
                )
            )
        }

        aircraftMetaDataContainer.addView(videoView)
        addDivider()
    }

    private fun addDivider() {
        val divider = View(this)
        divider.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, 1)
        divider.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryLight))
        aircraftMetaDataContainer.addView(divider)
    }

    private fun onScrollChanged(scrollY: Int) {
        aircraftImage.translationY = (scrollY / PARALLAX_FACTOR).toFloat()
        val toolbarAlphaScale = min(scrollY, aircraftImage.height) / aircraftImage.height.toFloat()
        val primaryColour = ContextCompat.getColor(this, R.color.colorPrimary)
        toolbar.setBackgroundColor(
            argb(
                (FULL_COLOUR * toolbarAlphaScale).toInt(),
                red(primaryColour),
                green(primaryColour),
                blue(primaryColour)
            )
        )
        toolbar.setTitleTextColor(
            argb(
                (FULL_COLOUR * toolbarAlphaScale).toInt(),
                FULL_COLOUR,
                FULL_COLOUR,
                FULL_COLOUR
            )
        )
        val toolbarMaxElevation = TOOLBAR_ELEVATION.dpToPixels()
        toolbar.elevation = min(scrollY, scrollView.paddingTop).toFloat() /
                toolbarMaxElevation.toFloat()
    }

    private fun navigateToPhotoCarousel() {
        navigator.launchPhotoCarouselActivity(aircraftId, aircraftImage)
    }

    private inner class EnterTransitionListener : TransitionListenerAdapter() {
        val slideDistance = SLIDE_DISTANCE.pixelsToDp().toFloat()

        override fun onTransitionStart(transition: Transition?) {
            super.onTransitionStart(transition)
            transitionSlidingViews.forEach { it.translationY = slideDistance }
            transitionFadingViews.forEach { it.alpha = 0.0f }
        }

        override fun onTransitionEnd(transition: Transition?) {
            super.onTransitionEnd(transition)
            aircraftDetailsContainer.setBackgroundColor(detailsBackgroundColour)
            toolbar.animate().alpha(1.0f).start()
            photoCarouselButton.animate().alpha(1.0f).start()
            val slideAnimators =
                transitionSlidingViews.map { ObjectAnimator.ofFloat(it, TRANSLATION_Y, 0.0f) }
            val fadeAnimators =
                transitionSlidingViews.map { ObjectAnimator.ofFloat(it, ALPHA, 1.0f) }
            AnimatorSet().apply {
                interpolator = LinearOutSlowInInterpolator()
                duration = ENTER_ANIMATION_DURATION
                playTogether(slideAnimators.append(fadeAnimators))
                start()
            }
        }
    }

    private companion object {
        private const val TRANSITION_TIMEOUT = 500L
        private const val TOOLBAR_ELEVATION = 4
        private const val PARALLAX_FACTOR = 4
        private const val FULL_COLOUR = 255
        private const val SLIDE_DISTANCE = 400
        private const val ENTER_ANIMATION_DURATION = 160L
    }
}

// TODO move
private fun <T> List<T>.append(list: List<T>): List<T> =
    this.toMutableList().apply {
        addAll(list)
    }
