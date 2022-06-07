package com.megaulorder.imananji.clock

sealed interface ClockController {

	fun setLocalTime(unixTime: Long)

	fun setApiTime(unixTime: Long, offset: Long, timeZone: String)

	class ClockControllerImpl(private val widget: ClockWidget) :
		ClockController {

		override fun setLocalTime(unixTime: Long) {
			widget.setTime(unixTime)
		}

		override fun setApiTime(unixTime: Long, offset: Long, timeZone: String) {
			widget.setTime(unixTime - offset, timeZone)
		}
	}
}
