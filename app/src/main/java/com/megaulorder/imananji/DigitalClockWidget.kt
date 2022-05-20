package com.megaulorder.imananji

import java.text.SimpleDateFormat
import java.util.*

class DigitalClockWidget(
	private val view: DigitalClockView
) {

	private val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPAN)

	fun setTime(unixTime: Long, timeZone: String? = TimeZone.getDefault().id) {
		formatter.timeZone = if (view.timeZone != null) {
			TimeZone.getTimeZone(view.timeZone)
		} else {
			TimeZone.getTimeZone(timeZone)
		}
		val result = formatter.format(unixTime)
		view.text = result
	}
}
