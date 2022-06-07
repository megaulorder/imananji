package com.megaulorder.imananji.mvi

sealed interface Event {
	data class Offset(val offset: Long) : Event
}

sealed interface Effect {
	data class Offset(val offset: Long) : Effect
}
