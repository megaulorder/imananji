package com.megaulorder.imananji

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.megaulorder.imananji.button.SwitchButtonController
import com.megaulorder.imananji.button.SwitchButtonWidget
import com.megaulorder.imananji.clock.ClockController.ClockControllerImpl
import com.megaulorder.imananji.clock.analog.AnalogClockWidget.AnalogClockNoTimeZoneWidget
import com.megaulorder.imananji.clock.analog.AnalogClockWidget.AnalogClockWithTimeZoneWidget
import com.megaulorder.imananji.clock.digital.DigitalClockWidget.DigitalClockNoTimeZoneWidget
import com.megaulorder.imananji.clock.digital.DigitalClockWidget.DigitalClockWithTimeZoneWidget
import com.megaulorder.imananji.difference.TimeDifferenceController
import com.megaulorder.imananji.difference.TimeDifferenceWidget
import com.megaulorder.imananji.mvi.Effect
import com.megaulorder.imananji.mvi.Event
import com.megaulorder.imananji.mvi.Reducer
import com.megaulorder.imananji.recievers.InternetAvailabilityReceiver
import com.megaulorder.imananji.recievers.TimeChangedReceiver
import kotlinx.coroutines.flow.MutableSharedFlow

class MainActivity : AppCompatActivity() {

	private lateinit var timeChangedReceiver: TimeChangedReceiver
	private lateinit var internetAvailabilityReceiver: InternetAvailabilityReceiver
	private val timeChangedFilter = IntentFilter(Intent.ACTION_TIME_CHANGED)
	private val internetAvailableFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val eventsFlow = MutableSharedFlow<Event>()
		val effectsFlow = MutableSharedFlow<Effect>()
		val reducer = Reducer(lifecycle.coroutineScope, eventsFlow, effectsFlow)

		val differenceWidget = TimeDifferenceWidget(findViewById(R.id.time_difference))
		val differenceController = TimeDifferenceController(
			widget = differenceWidget,
			resources = resources,
			coroutineScope = lifecycle.coroutineScope,
			effectsFlow = effectsFlow
		)

		val localDigitalWidget = DigitalClockNoTimeZoneWidget(findViewById(R.id.local_time_digital))
		val greenwichDigitalWidget =
			DigitalClockWithTimeZoneWidget(findViewById(R.id.greenwich_time_digital))

		val localAnalogWidget = AnalogClockNoTimeZoneWidget(findViewById(R.id.local_time_analog))
		val greenwichAnalogWidget =
			AnalogClockWithTimeZoneWidget(findViewById(R.id.greenwich_time_analog))

		val localDigitalController = ClockControllerImpl(localDigitalWidget)
		val greenwichDigitalController = ClockControllerImpl(greenwichDigitalWidget)

		val localAnalogController = ClockControllerImpl(localAnalogWidget)
		val greenwichAnalogController = ClockControllerImpl(greenwichAnalogWidget)

		val switchButtonWidget = SwitchButtonWidget(findViewById(R.id.switch_button))
		val switchButtonController = SwitchButtonController(
			widget = switchButtonWidget,
			digitalWidgets = listOf(localDigitalWidget, greenwichDigitalWidget),
			analogWidgets = listOf(localAnalogWidget, greenwichAnalogWidget)
		)

		val timeController = TimeController(
			coroutineScope = lifecycle.coroutineScope,
			repo = App.instance.repo,
			clockControllers = arrayListOf(
				localDigitalController,
				greenwichDigitalController,
				localAnalogController,
				greenwichAnalogController
			),
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
