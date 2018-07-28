package com.edwardharker.aircraftrecognition.ui.filter.picker

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.edwardharker.aircraftrecognition.R
import com.edwardharker.aircraftrecognition.model.Filter
import com.edwardharker.aircraftrecognition.model.FilterOption
import com.edwardharker.aircraftrecognition.ui.bind
import com.edwardharker.aircraftrecognition.ui.filter.selectedFilterOptions


internal class FilterPicker : LinearLayout {

    private val filterText by bind<TextView>(R.id.filter_text)
    private val filterOptionsRecyclerView by bind<RecyclerView>(R.id.filter_options_recycler_view)

    private val selectedFilterOptions = selectedFilterOptions()

    private var filter: Filter? = null

    var selectionListener: () -> Unit = {}

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        orientation = VERTICAL
        clipToPadding = false
        clipChildren = false
        from(context).inflate(R.layout.view_filter_picker, this)
        filterOptionsRecyclerView.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
    }

    fun bindTo(filter: Filter) {
        this.filter = filter
        filterText.text = filter.filterText
        filterOptionsRecyclerView.adapter = FilterOptionsAdapter(filter.name, filter.filterOptions)
    }

    private fun onFilterOptionClicked(filterOption: FilterOption) {
        filter?.let {
            if (selectedFilterOptions.isSelected(it.name, filterOption.value)) {
                selectedFilterOptions.deselect(it.name)
            } else {
                selectedFilterOptions.select(it.name, filterOption.value)
                selectionListener.invoke()
            }
            filterOptionsRecyclerView.adapter!!.notifyDataSetChanged()
        }
    }

    private inner class FilterOptionsAdapter(val filterName: String, val filterOptions: List<FilterOption>) : RecyclerView.Adapter<FilterOptionsAdapter.ViewHolder>() {

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val filterOption = filterOptions[position]
            holder.label.text = filterOption.label
            holder.label.isSelected = selectedFilterOptions.isSelected(filterName, filterOption.value)
        }

        override fun getItemCount(): Int = filterOptions.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                ViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_filter_option, parent, false))


        private inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val label: TextView by lazy { view.findViewById<TextView>(R.id.filter_label) }

            init {
                view.setOnClickListener {
                    if (adapterPosition >= 0) {
                        onFilterOptionClicked(filterOptions[adapterPosition])
                    }
                }
            }
        }
    }

}
