package com.megaulorder.imananji

import java.text.SimpleDateFormat
import java.util.*

class DigitalClockWidget(
	private val view: DigitalClockView
) {

	var timeZone: TimeZone? = null

	fun setTime(timestamp: Long) {
		val time = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ROOT)
		if (view.timeZone != null) {
			timeZone = TimeZone.getTimeZone(view.timeZone)
			time.timeZone = timeZone
		} else {
			timeZone = TimeZone.getDefault()
		}
		val result = time.format(timestamp)
		view.text = result
	}
}
