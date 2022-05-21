package com.megaulorder.imananji

import android.app.Application
import com.megaulorder.imananji.api.TimeApi

class App : Application() {

	lateinit var repo: TimeRepository

	override fun onCreate() {
		super.onCreate()
		instance = this

		repo = TimeRepository(TimeApi.create())
	}

	companion object {
		lateinit var instance: App
	}
}
