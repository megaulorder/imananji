package com.megaulorder.imananji

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TimeChangedReceiver(
	private val timeController: TimeController
) : BroadcastReceiver() {

	override fun onReceive(context: Context?, intent: Intent?) {
		timeController.updateTime()
	}
}
