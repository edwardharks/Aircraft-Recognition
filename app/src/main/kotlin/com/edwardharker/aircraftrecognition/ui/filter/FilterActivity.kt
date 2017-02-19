package com.edwardharker.aircraftrecognition.ui.filter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.support.v7.widget.Toolbar.OnMenuItemClickListener
import android.view.MenuItem
import com.edwardharker.aircraftrecognition.R
import com.edwardharker.aircraftrecognition.ui.filter.picker.FilterPickerRecyclerView
import com.edwardharker.aircraftrecognition.ui.filter.results.FilterResultsRecyclerView

class FilterActivity : AppCompatActivity(), OnMenuItemClickListener, FilterResultsRecyclerView.HiddenListener {

    val toolbar: Toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }
    val bottomSheetView: FilterResultsRecyclerView by lazy { findViewById(R.id.content_filter) as FilterResultsRecyclerView }
    val filterPicker: FilterPickerRecyclerView by lazy { findViewById(R.id.filter_picker) as FilterPickerRecyclerView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        toolbar.inflateMenu(R.menu.menu_filter)
        toolbar.setOnMenuItemClickListener(this)

        bottomSheetView.hiddenListener = this
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_toggle_bottom_sheet) {
            toggleBottomSheetHidden()
            return true
        }
        return false
    }

    override fun onBackPressed() {
        if (bottomSheetView.isHidden) {
            bottomSheetView.showBottomSheet()
        } else {
            super.onBackPressed()
        }
    }

    override fun onBottomSheetHiddenChanged(hidden: Boolean) {
        updateBottomSheetToggle(hidden)
        filterPicker.scrollOnSelection = !hidden
    }

    private fun toggleBottomSheetHidden() {
        if (bottomSheetView.isHidden) {
            bottomSheetView.showBottomSheet()
        } else {
            bottomSheetView.hideBottomSheet()
        }
    }

    private fun updateBottomSheetToggle(hidden: Boolean) {
        val item: MenuItem = toolbar.menu.findItem(R.id.action_toggle_bottom_sheet)
        if (hidden) {
            item.icon = getDrawable(R.drawable.ic_expand_less_white_24dp)
            item.title = getString(R.string.action_expand)
        } else {
            item.icon = getDrawable(R.drawable.ic_expand_more_white_24dp)
            item.title = getString(R.string.action_collapse)
        }
    }
}

