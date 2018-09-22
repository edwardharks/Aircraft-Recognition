package com.edwardharker.aircraftrecognition.repository.assets

import android.content.Context
import com.edwardharker.aircraftrecognition.androidcommon.R
import com.edwardharker.aircraftrecognition.gson.filtersListType
import com.edwardharker.aircraftrecognition.model.Filter
import com.edwardharker.aircraftrecognition.repository.FilterRepository
import com.google.gson.Gson
import rx.Observable

class AssetsFilterRepository(
    private val context: Context,
    private val gson: Gson
) : FilterRepository {
    override fun filters(): Observable<List<Filter>> {
        return Observable.fromCallable {
            val inputStream = context.resources.openRawResource(R.raw.filters)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            return@fromCallable gson.fromJson<List<Filter>?>(String(buffer), filtersListType)
        }
    }
}
