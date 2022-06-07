package com.megaulorder.imananji.clock.analog

import android.view.View
import com.megaulorder.imananji.clock.ClockWidget
import java.text.SimpleDateFormat
import java.util.*

private val formatter = SimpleDateFormat("HH:mm:ss", Locale.JAPAN)

sealed interface AnalogClockWidget : ClockWidget {

	class AnalogClockWithTimeZoneWidget(private val view: AnalogClockView) : AnalogClockWidget {

		override fun setTime(unixTime: Long, timeZone: String?) {
			formatter.timeZone = TimeZone.getTimeZone(view.timeZone)
			formatter.format(unixTime)
			view.hours = formatter.calendar.get(Calendar.HOUR)
			view.minutes = formatter.calendar.get(Calendar.MINUTE)
			view.seconds = formatter.calendar.get(Calendar.SECOND)
		}

		override fun show() {
			view.visibility = View.VISIBLE
		}

		override fun hide() {
			view.visibility = View.GONE
		}
	}

	class AnalogClockNoTimeZoneWidget(private val view: AnalogClockView) : AnalogClockWidget {

		override fun setTime(unixTime: Long, timeZone: String?) {
			formatter.timeZone = TimeZone.getDefault()
			formatter.format(unixTime)
			view.hours = formatter.calendar.get(Calendar.HOUR)
			view.minutes = formatter.calendar.get(Calendar.MINUTE)
			view.seconds = formatter.calendar.get(Calendar.SECOND)
		}

		override fun show() {
			view.visibility = View.VISIBLE
		}

		override fun hide() {
			view.visibility = View.GONE
		}
	}
}
