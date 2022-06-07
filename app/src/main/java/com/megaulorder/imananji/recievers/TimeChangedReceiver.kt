package com.megaulorder.imananji.recievers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.megaulorder.imananji.TimeController

class TimeChangedReceiver(
	private val timeController: TimeController
) : BroadcastReceiver() {

	override fun onReceive(context: Context?, intent: Intent?) {
		timeController.updateTime()
	}
}
