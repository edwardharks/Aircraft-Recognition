package com.edwardharker.aircraftrecognition.ui.aircraftdetail

import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.edwardharker.aircraftrecognition.aircraftdetail.R

class PageIndicator(private val textView: TextView) {
    private val listener = PageChangeListener()

    private var totalPages: Int = 0
        set(value) {
            field = value
            updateText()
        }
    private var currentPage: Int = 0
        set(value) {
            validateCurrentPage(value)
            field = value
            updateText()
        }

    private var viewPager: ViewPager? = null

    fun setViewPager(viewPager: ViewPager) {
        this.viewPager?.removeOnPageChangeListener(listener)
        this.viewPager = viewPager
        totalPages = viewPager.adapter?.count ?: 0
        currentPage = if (totalPages == 0) 0 else viewPager.currentItem + 1
        viewPager.addOnPageChangeListener(listener)
    }

    private fun updateText() {
        textView.text = textView.context.getString(R.string.page_indicator, currentPage, totalPages)
    }

    private fun validateCurrentPage(value: Int) {
        if (totalPages > 0) {
            require(value > 0) {
                "currentPage ($value) must be greater than 0"
            }
        } else {
            require(value == 0) {
                "currentPage ($value) must be 0 when totalPages is 0"
            }
        }
        require(value <= totalPages) {
            "currentPage ($value) must be less than or equal to totalPages ($totalPages)"
        }
    }

    private inner class PageChangeListener : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) = Unit

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) = Unit

        override fun onPageSelected(position: Int) {
            currentPage = if (totalPages == 0) 0 else position + 1
        }
    }
}
