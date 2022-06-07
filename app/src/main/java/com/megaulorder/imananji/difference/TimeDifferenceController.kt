package com.megaulorder.imananji.difference

import android.content.res.Resources
import androidx.lifecycle.LifecycleCoroutineScope
import com.megaulorder.imananji.R
import com.megaulorder.imananji.mvi.Effect
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 *  Controller for widget that displays difference between current time from server and device's time, if any.
 */
class TimeDifferenceController(
	private val widget: TimeDifferenceWidget,
	private val resources: Resources,
	private val coroutineScope: LifecycleCoroutineScope,
	private val effectsFlow: MutableSharedFlow<Effect>,
) {
	init {
		coroutineScope.launchWhenResumed { effectsFlow.collect(::handleEffect) }
	}

	private fun handleEffect(effect: Effect) {
		if (effect is Effect.Offset) {
			setDifference(effect.offset)
		}
	}

	private fun setDifference(offset: Long) {
		val formatter = SimpleDateFormat("HH:mm:ss:", Locale.JAPAN)
		formatter.timeZone = TimeZone.getTimeZone("GMT")

		val oneSecond: Long = TimeUnit.SECONDS.toMillis(1)

		widget.setText(
			when {
				offset > oneSecond -> "+${formatter.format(offset)}"
				offset < -oneSecond -> "-${formatter.format(TimeUnit.MILLISECONDS.toMillis(1) - offset)}"
				else -> resources.getString(R.string.no_difference)
			}
		)
	}
}
