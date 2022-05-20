package com.megaulorder.imananji

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class DigitalClockController(
	coroutineScope: LifecycleCoroutineScope,
	private val widget: DigitalClockWidget,
	private val repo: TimeRepository,
	private val differenceController: TimeDifferenceController?
) {

	init {
		coroutineScope.launch {
			when (val result = repo.getTimeData()) {
				is ResultWrapper.Error -> {
					while (true) {
						widget.setTime(System.currentTimeMillis())
						delay(1000)
					}
				}
				is ResultWrapper.Success -> {
					val currentTime = System.currentTimeMillis()
					val data: TimeData = result.value

					val offset: Long =
						currentTime - TimeUnit.SECONDS.toMillis(data.unixTime)

					differenceController?.let { differenceController.setDifference(offset) }

					while (true) {
						widget.setTime(System.currentTimeMillis() - offset, data.timeZone)
						delay(1000)
					}
				}
			}
		}
	}
}
