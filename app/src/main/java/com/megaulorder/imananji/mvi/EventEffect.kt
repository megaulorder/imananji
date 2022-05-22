package com.megaulorder.imananji.mvi

sealed interface Event {
	data class OffsetEvent(val offset: Long) : Event
}

sealed interface Effect {
	data class OffsetEffect(val offset: Long) : Effect
}
