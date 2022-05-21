package com.megaulorder.imananji

import com.megaulorder.imananji.api.ResultWrapper
import com.megaulorder.imananji.api.TimeApi

class TimeRepository(
	private val api: TimeApi,
) {

	suspend fun getTimeData(): ResultWrapper {
		return try {
			ResultWrapper.Success(api.getTime().body()!!)
		} catch (throwable: Throwable) {
			ResultWrapper.Error
		}
	}
}
