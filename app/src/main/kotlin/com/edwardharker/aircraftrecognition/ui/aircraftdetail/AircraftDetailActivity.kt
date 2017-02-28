package com.edwardharker.aircraftrecognition.ui.aircraftdetail

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
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
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.View.ALPHA
import android.view.View.TRANSLATION_Y
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import com.edwardharker.aircraftrecognition.R
import com.edwardharker.aircraftrecognition.aircraftdetail.AircraftDetailView
import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.ui.*
import java.lang.Math.min

class AircraftDetailActivity : AppCompatActivity(), AircraftDetailView {

    private val aircraftImage by lazy { findViewById(R.id.aircraft_image) as AspectRatioImageView }
    private val aircraftName by lazy { findViewById(R.id.aircraft_name) as TextView }
    private val aircraftDescription by lazy { findViewById(R.id.aircraft_description) as TextView }
    private val aircraftMetaDataContainer by lazy { findViewById(R.id.aircraft_meta_data_container) as ViewGroup }
    private val toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }
    private val scrollView by lazy { findViewById(R.id.scroll_view) as NestedScrollView }

    private val presenter = aircraftDetailPresenter()

    companion object {
        private val aircraftIdExtra = "aircraftId"

        fun startActivity(activity: Activity, aircraftId: String, aircraftImage: View, background: View, aircraftName: View) {
            val intent = Intent(activity, AircraftDetailActivity::class.java)
            intent.putExtra(aircraftIdExtra, aircraftId)
            activity.startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(
                            activity,
                            Pair(aircraftImage, activity.getString(R.string.transition_aircraft_image)),
                            Pair(background, activity.getString(R.string.transition_aircraft_detail_background)),
                            Pair(aircraftName, activity.getString(R.string.transition_aircraft_name))
                    ).toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aircraft_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ""
        toolbar.setNavigationOnClickListener { onBackPressed() }

        supportPostponeEnterTransition()

        if (savedInstanceState == null) {
            toolbar.alpha = 0.0f
            aircraftDescription.alpha = 0.0f
            aircraftMetaDataContainer.alpha = 0.0f
            window.sharedElementEnterTransition.addListener(EnterTransitionListener())
        }

        scrollView.setOnScrollChangeListener {
            nestedScrollView: NestedScrollView?, x: Int, y: Int, oldX: Int, oldY: Int ->
            onScrollChanged(y)
        }

        scrollView.scrollTo(0, 0)
        onScrollChanged(0)

        aircraftImage.viewTreeObserver.addOnPreDrawListener {
            onAircraftImagePreDraw()
            return@addOnPreDrawListener true
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.startPresenting(this, intent.getStringExtra(aircraftIdExtra))
    }

    override fun onStop() {
        super.onStop()
        presenter.stopPresenting()
    }

    override fun onBackPressed() {
        if (scrollView.scrollY > 0) {
            finish()
        } else {
            supportFinishAfterTransition()
        }
    }

    override fun showAircraft(aircraft: Aircraft) {
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
        aircraft.metaData.forEach { addAircraftMetaDataItem(it.key, it.value) }
        addAircraftMetaDataItem(getString(R.string.attribution), aircraft.attribution) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(aircraft.attributionUrl)))
        }
    }

    private fun addAircraftMetaDataItem(label: String, value: String, onClick: (() -> Unit)? = null) {
        val metaDataView = LayoutInflater.from(this).inflate(R.layout.view_meta_data_item, aircraftMetaDataContainer, false) as TextView
        val spannable = SpannableString(getString(R.string.meta_data_item, label, value))
        spannable.setSpan(StyleSpan(BOLD), spannable.length - value.length, spannable.length, 0)
        metaDataView.text = spannable
        onClick?.let { metaDataView.setOnClickListener { onClick.invoke() } }
        val divider = View(this)
        divider.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, 1)
        divider.setBackgroundColor(getColor(this, R.color.colorPrimaryLight))
        aircraftMetaDataContainer.addView(metaDataView)
        aircraftMetaDataContainer.addView(divider)
    }

    private fun onScrollChanged(scrollY: Int) {
        aircraftImage.translationY = -(scrollY / 4).toFloat()
        val toolbarAlphaScale = min(scrollY, scrollView.paddingTop) / scrollView.paddingTop.toFloat()
        val primaryColour = getColor(this, R.color.colorPrimary)
        toolbar.setBackgroundColor(argb((255 * toolbarAlphaScale).toInt(),
                red(primaryColour), green(primaryColour), blue(primaryColour)))
        toolbar.setTitleTextColor(argb((255 * toolbarAlphaScale).toInt(), 255, 255, 255))
        val toolbarMaxElevation = dpToPixels(8)
        toolbar.elevation = min(scrollY, scrollView.paddingTop).toFloat() / toolbarMaxElevation.toFloat()
    }

    private fun onAircraftImagePreDraw() {
        scrollView.paddingTop2 = if (aircraftImage.height > screenHeight()) {
            screenHeight() - screenHeight() / 4
        } else {
            aircraftImage.height
        }
    }

    private inner class EnterTransitionListener : TransitionListenerAdapter() {

        val slideDistance = pixelsToDp(400).toFloat()

        override fun onTransitionStart(transition: Transition?) {
            super.onTransitionStart(transition)
            aircraftDescription.translationY = slideDistance
            aircraftMetaDataContainer.translationY = slideDistance
            aircraftDescription.alpha = 0.0f
            aircraftMetaDataContainer.alpha = 0.0f
        }

        override fun onTransitionEnd(transition: Transition?) {
            super.onTransitionEnd(transition)
            toolbar.animate().alpha(1.0f).start()
            val slideUp = AnimatorSet()
            slideUp.interpolator = LinearOutSlowInInterpolator()
            slideUp.duration = 160
            slideUp.playTogether(
                    ObjectAnimator.ofFloat(aircraftDescription, TRANSLATION_Y, 0.0f),
                    ObjectAnimator.ofFloat(aircraftMetaDataContainer, TRANSLATION_Y, 0.0f),
                    ObjectAnimator.ofFloat(aircraftDescription, ALPHA, 1.0f),
                    ObjectAnimator.ofFloat(aircraftMetaDataContainer, ALPHA, 1.0f)
            )
            slideUp.start()
        }
    }
}

