package com.edwardharker.aircraftrecognition.android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_MY_PACKAGE_REPLACED

class AppUpdateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && intent.action == ACTION_MY_PACKAGE_REPLACED) {
            firstInstall().saveVersion()
        }
    }
}
