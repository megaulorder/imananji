package com.megaulorder.imananji.clock

interface ClockWidget {

	fun setTime(unixTime: Long, timeZone: String? = null)

	fun show()

	fun hide()
}
