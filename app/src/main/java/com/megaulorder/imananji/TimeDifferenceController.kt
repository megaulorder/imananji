package com.megaulorder.imananji

import java.util.*
import kotlin.time.Duration.Companion.milliseconds

class TimeDifferenceController(
	widget: TimeDifferenceWidget
) {

	init {
		widget.setText(TimeZone.getDefault().rawOffset.milliseconds)
	}
}
