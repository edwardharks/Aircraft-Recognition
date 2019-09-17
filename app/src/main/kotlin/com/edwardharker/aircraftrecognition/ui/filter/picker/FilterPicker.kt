package com.edwardharker.aircraftrecognition.ui.filter.picker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    var selectionListener: (filter: String, filterOption: String) -> Unit = { _, _ -> }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        orientation = VERTICAL
        clipToPadding = false
        clipChildren = false
        from(context).inflate(R.layout.view_filter_picker, this)
        filterOptionsRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
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
                selectionListener.invoke(it.name, filterOption.value)
            }
            filterOptionsRecyclerView.adapter!!.notifyDataSetChanged()
        }
    }

    private inner class FilterOptionsAdapter(
        val filterName: String,
        val filterOptions: List<FilterOption>
    ) : RecyclerView.Adapter<FilterOptionsAdapter.ViewHolder>() {

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val filterOption = filterOptions[position]
            holder.label.text = filterOption.label
            holder.itemView.isSelected = selectedFilterOptions
                .isSelected(filterName, filterOption.value)
            val imageRes = filterOption.imageRes
            if (imageRes != null) {
                holder.image.setImageResource(imageRes)
            } else {
                holder.image.setImageDrawable(null)
            }
        }

        override fun getItemCount(): Int = filterOptions.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                from(parent.context).inflate(R.layout.view_filter_option, parent, false)
            )


        private inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val label: TextView by lazy { view.findViewById<TextView>(R.id.filter_label) }
            val image: ImageView by lazy { view.findViewById<ImageView>(R.id.filter_image) }

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
