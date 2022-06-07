package com.megaulorder.imananji.button

import com.megaulorder.imananji.clock.analog.AnalogClockWidget
import com.megaulorder.imananji.clock.digital.DigitalClockWidget

class SwitchButtonController(
	private val widget: SwitchButtonWidget,
	private val digitalWidgets: List<DigitalClockWidget>,
	private val analogWidgets: List<AnalogClockWidget>,
) {

	private var isDigital: Boolean = true

	init {
		widget.clickListener = { switch() }
	}

	private fun switch() {
		isDigital = if (isDigital) {
			widget.showDigital()
			analogWidgets.forEach { it.show() }
			digitalWidgets.forEach { it.hide() }
			false
		} else {
			widget.showAnalog()
			analogWidgets.forEach { it.hide() }
			digitalWidgets.forEach { it.show() }
			true
		}
	}
}
