package com.megaulorder.imananji.recievers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.megaulorder.imananji.TimeController

class InternetAvailabilityReceiver(
	private val timeController: TimeController
) : BroadcastReceiver() {

	private var connectionAvailableOnStart: Boolean = false

	override fun onReceive(context: Context?, intent: Intent?) {
		val manager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

		val netInfo = manager.activeNetworkInfo
		if (netInfo != null && netInfo.isConnected) {
			connectionAvailableOnStart = true
			timeController.updateTime()
		} else if (!connectionAvailableOnStart) {
			timeController.updateTime()
		}
	}
}
