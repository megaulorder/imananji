package com.megaulorder.imananji

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DigitalClockController(
	lifecycle: Lifecycle,
	private val widget: DigitalClockWidget
) {

	init {
		lifecycle.coroutineScope.launch {
			while (true) {
				widget.setTime(System.currentTimeMillis())
				delay(1000)
			}
		}
	}
}
