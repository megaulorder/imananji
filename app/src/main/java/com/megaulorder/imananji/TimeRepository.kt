package com.megaulorder.imananji

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
