package com.megaulorder.imananji.api

sealed class ResultWrapper {
	data class Success(val value: TimeData) : ResultWrapper()
	object Error : ResultWrapper()
}
