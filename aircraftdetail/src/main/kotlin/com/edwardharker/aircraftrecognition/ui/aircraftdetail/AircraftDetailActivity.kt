package com.edwardharker.aircraftrecognition.ui.aircraftdetail

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color.*
import android.graphics.Typeface.BOLD
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat.getColor
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.SpannableString
import android.text.style.StyleSpan
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.View.ALPHA
import android.view.View.TRANSLATION_Y
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import com.edwardharker.aircraftrecognition.aircraftdetail.AircraftDetailView
import com.edwardharker.aircraftrecognition.aircraftdetail.AircraftDetailViewModel
import com.edwardharker.aircraftrecognition.aircraftdetail.R
import com.edwardharker.aircraftrecognition.analytics.aircraftDetailEvent
import com.edwardharker.aircraftrecognition.analytics.aircraftDetailScreen
import com.edwardharker.aircraftrecognition.analytics.eventAnalytics
import com.edwardharker.aircraftrecognition.ui.*
import com.edwardharker.aircraftrecognition.youtube.youtubeStandalonePlayerHelper
import java.lang.Math.min

private val aircraftIdExtra = "aircraftId"
private val startedWithTransitionExtra = "startedWithTransition"

fun ActivityLauncher.launchAircraftDetailActivity(aircraftId: String, aircraftImage: View, background: View, aircraftName: View) {
    val intent = Intent(activity, AircraftDetailActivity::class.java).apply {
        putExtra(aircraftIdExtra, aircraftId)
        putExtra(startedWithTransitionExtra, true)
    }
    activity.startActivity(intent,
            ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    android.util.Pair(aircraftImage, activity.getString(R.string.transition_aircraft_image)),
                    android.util.Pair(background, activity.getString(R.string.transition_aircraft_detail_background)),
                    android.util.Pair(aircraftName, activity.getString(R.string.transition_aircraft_name))
            ).toBundle())
}

fun ActivityLauncher.launchAircraftDetailActivity(aircraftId: String) {
    val intent = Intent(activity, AircraftDetailActivity::class.java).apply {
        putExtra(aircraftIdExtra, aircraftId)
    }
    activity.startActivity(intent)
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

    private val transitionSlidingViews by lazy {
        listOf(aircraftDescription,
                aircraftMetaDataContainer,
                similarAircraftRail)
    }

    private val transitionFadingViews by lazy {
        listOf(aircraftMetaDataContainer,
                aircraftDescription,
                aircraftMetaDataContainer)
    }

    private val aircraftId: String by lazy {
        if (intent.hasExtra(aircraftIdExtra)) {
            intent.getStringExtra(aircraftIdExtra)
        } else if (intent.data != null) {
            intent.data.lastPathSegment
        } else {
            finish()
            ""
        }
    }

    private val startedWithTransition by lazy { intent.getBooleanExtra(startedWithTransitionExtra, false) }

    private val isFromDeepLink by lazy { intent.data != null }
    private var animateOnBackPressed = true

    private val presenter = aircraftDetailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
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
        animateOnBackPressed = savedInstanceState == null

        if (savedInstanceState == null && startedWithTransition) {
            toolbar.alpha = 0.0f
            aircraftDescription.alpha = 0.0f
            aircraftMetaDataContainer.alpha = 0.0f
            photoCarouselButton.alpha = 0.0f
            window.sharedElementEnterTransition.addListener(EnterTransitionListener())
        }

        scrollView.setOnScrollChangeListener { nestedScrollView: NestedScrollView?, x: Int, y: Int, oldX: Int, oldY: Int ->
            onScrollChanged(y)
        }

        scrollView.scrollTo(0, 0)
        onScrollChanged(0)

        aircraftImageContainer.setOnClickListener(this::navigateToPhotoCarousel)

        similarAircraftRail.aircraftId = aircraftId
    }

    override fun onStart() {
        super.onStart()
        presenter.startPresenting(this, aircraftId)
        eventAnalytics().logScreenView(aircraftDetailScreen())
        eventAnalytics().logEvent(aircraftDetailEvent(aircraftId))
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

    override fun showAircraft(aircraftDetailViewModel: AircraftDetailViewModel) {
        val aircraft = aircraftDetailViewModel.aircraft

        aircraftImage.loadAircraftImage(aircraft) {
            aircraftImage.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
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
    }

    private fun addAircraftMetaDataItem(label: String,
                                        value: String,
                                        onClick: (() -> Unit)? = null) {
        val metaDataView = LayoutInflater.from(this).inflate(R.layout.view_meta_data_item, aircraftMetaDataContainer, false) as TextView
        val spannable = SpannableString(getString(R.string.meta_data_item, label, value))
        spannable.setSpan(StyleSpan(BOLD), spannable.length - value.length, spannable.length, 0)
        metaDataView.text = spannable
        onClick?.let { metaDataView.setOnClickListener { onClick.invoke() } }
        aircraftMetaDataContainer.addView(metaDataView)
        addDivider()
    }

    private fun addWatchOnYoutubeItem(videoId: String) {
        val videoView = LayoutInflater.from(this).inflate(R.layout.view_aircraft_featured_video, aircraftMetaDataContainer, false)
        videoView.setOnClickListener { youtubeStandalonePlayerHelper().launchYoutubeStandalonePlayer(this, videoId) }
        aircraftMetaDataContainer.addView(videoView)
        addDivider()
    }

    private fun addDivider() {
        val divider = View(this)
        divider.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, 1)
        divider.setBackgroundColor(getColor(this, R.color.colorPrimaryLight))
        aircraftMetaDataContainer.addView(divider)
    }

    private fun onScrollChanged(scrollY: Int) {
        aircraftImage.translationY = (scrollY / 4).toFloat()
        val toolbarAlphaScale = min(scrollY, aircraftImage.height) / aircraftImage.height.toFloat()
        val primaryColour = getColor(this, R.color.colorPrimary)
        toolbar.setBackgroundColor(argb((255 * toolbarAlphaScale).toInt(),
                red(primaryColour), green(primaryColour), blue(primaryColour)))
        toolbar.setTitleTextColor(argb((255 * toolbarAlphaScale).toInt(), 255, 255, 255))
        val toolbarMaxElevation = 4.dpToPixels()
        toolbar.elevation = min(scrollY, scrollView.paddingTop).toFloat() / toolbarMaxElevation.toFloat()
    }

    private fun navigateToPhotoCarousel(view: View) {
        activityLauncher().launchPhotoCarouselActivity(aircraftId, aircraftImage)
    }

    private inner class EnterTransitionListener : TransitionListenerAdapter() {

        val slideDistance = 400.pixelsToDp().toFloat()

        override fun onTransitionStart(transition: Transition?) {
            super.onTransitionStart(transition)
            transitionSlidingViews.forEach { it.translationY = slideDistance }
            transitionFadingViews.forEach { it.alpha = 0.0f }
        }

        override fun onTransitionEnd(transition: Transition?) {
            super.onTransitionEnd(transition)
            toolbar.animate().alpha(1.0f).start()
            photoCarouselButton.animate().alpha(1.0f).start()
            val slideUp = AnimatorSet()
            slideUp.interpolator = LinearOutSlowInInterpolator()
            slideUp.duration = 160
            val slideAnimators = transitionSlidingViews.map { ObjectAnimator.ofFloat(it, TRANSLATION_Y, 0.0f) }
            val fadeAnimators = transitionSlidingViews.map { ObjectAnimator.ofFloat(it, ALPHA, 1.0f) }
            slideUp.playTogether(slideAnimators.append(fadeAnimators))
            slideUp.start()
        }
    }
}

private fun <T> List<T>.append(list: List<T>): List<T> =
        this.toMutableList().apply {
            addAll(list)
        }
