package com.edwardharker.aircraftrecognition.android.yearclass

import android.content.Context
import com.facebook.device.yearclass.YearClass

class YearClassProvider(private val context: Context) {
    val yearClass: Int by lazy { YearClass.get(context) }
}
