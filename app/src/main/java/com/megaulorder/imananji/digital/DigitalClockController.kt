package com.megaulorder.imananji.digital

sealed interface DigitalClockController {

	fun setLocalTime(unixTime: Long)

	fun setApiTime(unixTime: Long, offset: Long, timeZone: String)

	class DigitalClockControllerImpl(private val widget: DigitalClockWidget) :
		DigitalClockController {

		override fun setLocalTime(unixTime: Long) {
			widget.setTime(unixTime)
		}

		override fun setApiTime(unixTime: Long, offset: Long, timeZone: String) {
			widget.setTime(unixTime - offset, timeZone)
		}
	}
}
