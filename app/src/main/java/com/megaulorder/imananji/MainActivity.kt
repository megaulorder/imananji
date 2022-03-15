package com.megaulorder.imananji

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main_digital)

		val localWidget = DigitalClockWidget(findViewById(R.id.localTime))
		val localController = DigitalClockController(lifecycle, localWidget)

		val greenwichWidget = DigitalClockWidget(findViewById(R.id.greenwichTime))
		val greenwichController = DigitalClockController(lifecycle, greenwichWidget)

		val differenceWidget = TimeDifferenceWidget(findViewById(R.id.timeDifference))
		val differenceController = TimeDifferenceController(differenceWidget)
	}
}
