package com.megaulorder.imananji

import androidx.lifecycle.LifecycleCoroutineScope
import com.megaulorder.imananji.api.ResultWrapper
import com.megaulorder.imananji.api.TimeData
import com.megaulorder.imananji.digital.DigitalClockController.DigitalClockControllerImpl
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TimeController(
	private val coroutineScope: LifecycleCoroutineScope,
	private val repo: TimeRepository,
	private val digitalClockControllers: ArrayList<DigitalClockControllerImpl>,
	private val eventsFlow: MutableSharedFlow<Event>
) {

	private var job: Job? = null

	fun updateTime() {
		job?.cancel()

		job = coroutineScope.launch {
			when (val result = repo.getTimeData()) {
				is ResultWrapper.Error -> {
					while (true) {
						digitalClockControllers.map { it.setLocalTime(System.currentTimeMillis()) }
						delay(1000)
					}
				}
				is ResultWrapper.Success -> {
					val currentTime = System.currentTimeMillis()
					val data: TimeData = result.value

					val offset: Long =
						currentTime - TimeUnit.SECONDS.toMillis(data.unixTime)

					eventsFlow.emit(Event.OffsetEvent(offset))

					while (true) {
						digitalClockControllers.map {
							it.setApiTime(
								unixTime = System.currentTimeMillis(),
								offset = offset,
								timeZone = data.timeZone
							)
						}
						delay(1000)
					}
				}
			}
		}
	}
}
