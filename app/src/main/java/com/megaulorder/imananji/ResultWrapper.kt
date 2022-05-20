package com.megaulorder.imananji

sealed class ResultWrapper {
	data class Success(val value: TimeData) : ResultWrapper()
	object Error : ResultWrapper()
}
