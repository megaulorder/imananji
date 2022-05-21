package com.megaulorder.imananji.digital

import java.text.SimpleDateFormat
import java.util.*

private val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPAN)

sealed interface DigitalClockWidget {

	fun setTime(unixTime: Long, timeZone: String? = null)

	class DigitalClockWithTimeZoneWidget(private val view: DigitalClockView) : DigitalClockWidget {

		override fun setTime(unixTime: Long, timeZone: String?) {
			formatter.timeZone = TimeZone.getTimeZone(view.timeZone)
			view.text = formatter.format(unixTime)
		}
	}

	class DigitalClockNoTimeZoneWidget(private val view: DigitalClockView) : DigitalClockWidget {

		override fun setTime(unixTime: Long, timeZone: String?) {
			formatter.timeZone = TimeZone.getDefault()
			view.text = formatter.format(unixTime)
		}
	}
}
