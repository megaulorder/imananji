package com.megaulorder.imananji.mvi

import androidx.lifecycle.LifecycleCoroutineScope
import com.megaulorder.imananji.Effect
import com.megaulorder.imananji.Event
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect

class Reducer(
	private val coroutineScope: LifecycleCoroutineScope,
	private val eventFlow: MutableSharedFlow<Event>,
	private val effectFlow: MutableSharedFlow<Effect>
) {
	init {
		coroutineScope.launchWhenResumed { eventFlow.collect(::handleEvent) }
	}

	private fun handleEvent(event: Event) {
		if (event is Event.OffsetEvent) {
			effectFlow.emit(coroutineScope, Effect.OffsetEffect(event.offset))
		}
	}
}
