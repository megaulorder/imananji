package com.megaulorder.imananji.clock.digital

import android.view.View
import com.megaulorder.imananji.clock.ClockWidget
import java.text.SimpleDateFormat
import java.util.*

private val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPAN)

sealed interface DigitalClockWidget : ClockWidget {

	class DigitalClockWithTimeZoneWidget(private val view: DigitalClockView) : DigitalClockWidget {

		override fun setTime(unixTime: Long, timeZone: String?) {
			formatter.timeZone = TimeZone.getTimeZone(view.timeZone)
			view.text = formatter.format(unixTime)
		}

		override fun show() {
			view.visibility = View.VISIBLE
		}

		override fun hide() {
			view.visibility = View.GONE
		}
	}

	class DigitalClockNoTimeZoneWidget(private val view: DigitalClockView) : DigitalClockWidget {

		override fun setTime(unixTime: Long, timeZone: String?) {
			formatter.timeZone = TimeZone.getDefault()
			view.text = formatter.format(unixTime)
		}

		override fun show() {
			view.visibility = View.VISIBLE
		}

		override fun hide() {
			view.visibility = View.GONE
		}
	}
}
