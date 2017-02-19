package com.edwardharker.aircraftrecognition.ui.aircraftdetail

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color.*
import android.graphics.Typeface.BOLD
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat.getColor
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.SpannableString
import android.text.style.StyleSpan
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
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

        fun startActivity(activity: Activity, aircraftId: String, aircraftImage: View) {
            val intent = Intent(activity, AircraftDetailActivity::class.java)
            intent.putExtra(aircraftIdExtra, aircraftId)
            activity.startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(
                            activity,
                            aircraftImage,
                            activity.getString(R.string.transition_aircraft_image)
                    ).toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aircraft_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ""
        toolbar.setNavigationOnClickListener { supportFinishAfterTransition() }

        supportPostponeEnterTransition()

        if (savedInstanceState == null) {
            toolbar.alpha = 0.0f
            window.enterTransition.addListener(EnterTransitionListener())
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

    override fun showAircraft(aircraft: Aircraft) {
        aircraftImage.loadAircraftImage(aircraft) { supportStartPostponedEnterTransition() }
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
        override fun onTransitionEnd(transition: Transition?) {
            super.onTransitionEnd(transition)
            toolbar.animate().alpha(1.0f).start()
        }
    }
}

