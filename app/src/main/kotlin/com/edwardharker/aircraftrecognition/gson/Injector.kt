package com.edwardharker.aircraftrecognition.gson

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.model.Filter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

val aircraftListType: Type = object : TypeToken<ArrayList<Aircraft>>() {}.type
val filtersListType: Type = object : TypeToken<ArrayList<Filter>>() {}.type

fun gson(): Gson = GsonBuilder().create()