package com.megaulorder.imananji

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.megaulorder.imananji.difference.TimeDifferenceController
import com.megaulorder.imananji.difference.TimeDifferenceWidget
import com.megaulorder.imananji.digital.DigitalClockController.DigitalClockControllerImpl
import com.megaulorder.imananji.digital.DigitalClockWidget.DigitalClockNoTimeZoneWidget
import com.megaulorder.imananji.digital.DigitalClockWidget.DigitalClockWithTimeZoneWidget
import com.megaulorder.imananji.mvi.Effect
import com.megaulorder.imananji.mvi.Event
import com.megaulorder.imananji.mvi.Reducer
import kotlinx.coroutines.flow.MutableSharedFlow

class MainActivity : AppCompatActivity() {

	private lateinit var timeChangedReceiver: TimeChangedReceiver
	private lateinit var internetAvailabilityReceiver: InternetAvailabilityReceiver
	private val timeChangedFilter = IntentFilter(Intent.ACTION_TIME_CHANGED)
	private val internetAvailableFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main_digital)

		val eventsFlow = MutableSharedFlow<Event>()
		val effectsFlow = MutableSharedFlow<Effect>()
		val reducer = Reducer(lifecycle.coroutineScope, eventsFlow, effectsFlow)

		val differenceWidget = TimeDifferenceWidget(findViewById(R.id.timeDifference))
		val differenceController = TimeDifferenceController(
			widget = differenceWidget,
			resources = resources,
			coroutineScope = lifecycle.coroutineScope,
			effectsFlow = effectsFlow
		)

		val localWidget = DigitalClockNoTimeZoneWidget(findViewById(R.id.localTime))
		val greenwichWidget = DigitalClockWithTimeZoneWidget(findViewById(R.id.greenwichTime))

		val localController = DigitalClockControllerImpl(localWidget)
		val greenwichController = DigitalClockControllerImpl(greenwichWidget)

		val timeController = TimeController(
			coroutineScope = lifecycle.coroutineScope,
			repo = App.instance.repo,
			digitalClockControllers = arrayListOf(localController, greenwichController),
			eventsFlow = eventsFlow
		)

		timeChangedReceiver = TimeChangedReceiver(timeController)
		internetAvailabilityReceiver = InternetAvailabilityReceiver(timeController)
		registerReceiver(timeChangedReceiver, timeChangedFilter)
		registerReceiver(internetAvailabilityReceiver, internetAvailableFilter)
	}

	override fun onDestroy() {
		super.onDestroy()
		unregisterReceiver(timeChangedReceiver)
		unregisterReceiver(internetAvailabilityReceiver)
	}
}
