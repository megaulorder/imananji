package com.megaulorder.imananji

import android.content.res.Resources
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 *  Controller for widget that displays difference between time from server and local phone time, if any.
 */
class TimeDifferenceController(
	private val widget: TimeDifferenceWidget,
	private val resources: Resources,
) {
	fun setDifference(offset: Long) {
		val formatter = SimpleDateFormat("HH:mm:ss:", Locale.JAPAN)
		formatter.timeZone = TimeZone.getTimeZone("GMT")

		widget.setText(
			when {
				offset > TimeUnit.SECONDS.toMillis(1) -> "+${formatter.format(offset)}"
				offset < 0 -> "-${formatter.format(TimeUnit.MILLISECONDS.toMillis(1) - offset)}"
				else -> resources.getString(R.string.no_difference)
			}
		)
	}
}
