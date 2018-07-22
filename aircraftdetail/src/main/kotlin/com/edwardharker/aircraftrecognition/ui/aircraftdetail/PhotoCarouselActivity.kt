package com.edwardharker.aircraftrecognition.ui.aircraftdetail

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.edwardharker.aircraftrecognition.aircraftdetail.PhotoCarouselView
import com.edwardharker.aircraftrecognition.aircraftdetail.R
import com.edwardharker.aircraftrecognition.analytics.eventAnalytics
import com.edwardharker.aircraftrecognition.analytics.photoCarouselScreen
import com.edwardharker.aircraftrecognition.extension.postDelayed
import com.edwardharker.aircraftrecognition.model.Image
import com.edwardharker.aircraftrecognition.ui.Navigator
import com.edwardharker.aircraftrecognition.ui.AspectRatioImageView
import com.edwardharker.aircraftrecognition.ui.bind
import com.edwardharker.aircraftrecognition.ui.dpToPixels
import com.edwardharker.aircraftrecognition.ui.loadAircraftImage
import com.pixelcan.inkpageindicator.InkPageIndicator

private val aircraftIdExtra = "aircraftId"

fun Navigator.launchPhotoCarouselActivity(aircraftId: String, aircraftImage: View) {
    val intent = Intent(activity, PhotoCarouselActivity::class.java).apply {
        putExtra(aircraftIdExtra, aircraftId)
    }
    activity.startActivity(intent,
            ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    android.util.Pair(aircraftImage, activity.getString(R.string.transition_aircraft_image))
            ).toBundle())
}

class PhotoCarouselActivity : AppCompatActivity(), PhotoCarouselView {

    private val currentImagePositionKey = "currentImagePosition"

    private val toolbar by bind<Toolbar>(R.id.toolbar)
    private val viewPager by bind<ViewPager>(R.id.view_pager)
    private val indicator by bind<InkPageIndicator>(R.id.indicator)
    private val presenter = photoCarouselPresenter()

    private var images: List<Image> = emptyList()
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_carousel)

        supportPostponeEnterTransition()
        Handler().postDelayed(TRANSITION_TIMEOUT) {
            supportStartPostponedEnterTransition()
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ""
        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp)

        viewPager.adapter = CarouselAdapter()
        viewPager.pageMargin = 24.dpToPixels()

        page = savedInstanceState?.getInt(currentImagePositionKey) ?: 0
    }

    override fun onStart() {
        super.onStart()
        presenter.startPresenting(this, intent.getStringExtra(aircraftIdExtra))
        eventAnalytics().logScreenView(photoCarouselScreen())
    }

    override fun onStop() {
        super.onStop()
        presenter.stopPresenting()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(currentImagePositionKey, viewPager.currentItem)
    }

    override fun onBackPressed() {
        if (viewPager.currentItem > 0) {
            finish()
            overridePendingTransition(0, R.anim.fade_out)
        } else {
            supportFinishAfterTransition()
        }
    }

    override fun showImages(images: List<Image>) {
        this.images = images
        viewPager.adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(page, false)
        if (images.isNotEmpty()) {
            // InkPageIndicator crashes for a view pager with 0 items :/
            indicator.setViewPager(viewPager)
        }
    }

    private inner class CarouselAdapter : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val layout = LayoutInflater.from(container.context)
                    .inflate(R.layout.view_photo_carousel_image, container, false)
            val imageView = layout.findViewById<AspectRatioImageView>(R.id.aircraft_image)
            imageView.setZoomable(true)
            imageView.fixedAspectRatio = true
            imageView.loadAircraftImage(images[position]) {
                imageView.fixedAspectRatio = false
                if (position == 0) {
                    startPostponedTransitionFor(imageView)
                }
            }
            container.addView(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            return layout
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(obj as View)
        }

        override fun isViewFromObject(view: View?, obj: Any?): Boolean = view == obj

        override fun getCount(): Int = images.size

        private fun startPostponedTransitionFor(view: View) {
            view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    view.transitionName = getString(R.string.transition_aircraft_image)
                    supportStartPostponedEnterTransition()
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

    companion object {
        private const val TRANSITION_TIMEOUT = 500L
    }
}
