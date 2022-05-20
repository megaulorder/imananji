package com.megaulorder.imananji

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

sealed interface TimeApi {

	@GET("api/ip")
	suspend fun getTime(): Response<TimeData>

	companion object {

		var BASE_URL = "https://worldtimeapi.org/"

		fun create(): TimeApi {
			val retrofit = Retrofit.Builder()
				.addConverterFactory(GsonConverterFactory.create())
				.baseUrl(BASE_URL)
				.build()

			return retrofit.create(TimeApi::class.java)
		}
	}
}
