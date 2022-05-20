package com.megaulorder.imananji

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main_digital)

		val differenceWidget = TimeDifferenceWidget(findViewById(R.id.timeDifference))
		val differenceController = TimeDifferenceController(differenceWidget, resources)

		val localWidget = DigitalClockWidget(findViewById(R.id.localTime))
		val localController =
			DigitalClockController(
				lifecycle.coroutineScope,
				localWidget,
				App.instance.repo,
				differenceController
			)

		val greenwichWidget = DigitalClockWidget(findViewById(R.id.greenwichTime))
		val greenwichController = DigitalClockController(
			lifecycle.coroutineScope,
			greenwichWidget,
			App.instance.repo,
			null
		)
	}
}
