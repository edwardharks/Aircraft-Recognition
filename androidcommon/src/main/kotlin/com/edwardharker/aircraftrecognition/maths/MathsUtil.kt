package com.edwardharker.aircraftrecognition.maths

import kotlin.math.max
import kotlin.math.min

fun mapFromPercent(pc: Float, lBound: Float, uBound: Float): Float {
    return map(pc, 0f, 1f, lBound, uBound)
}

fun mapToPercent(value: Float, lBound: Float, uBound: Float): Float {
    return map(value, lBound, uBound, 0f, 1f)
}

fun map(
    value: Float,
    iStart: Float, iEnd: Float,
    oStart: Float, oEnd: Float
): Float {
    require(!(iStart > iEnd || oStart > oEnd)) { "Range is inverted" }
    val unboundedMap = oStart + (oEnd - oStart) * ((value - iStart) / (iEnd - iStart))
    return min(max(unboundedMap, oStart), oEnd)
}
