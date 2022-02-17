package com.megaulorder.imananji

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val localTime: TextView = findViewById(R.id.localTime)
		val greenwichTime: TextView = findViewById(R.id.greenwichTime)
		val timeDifference: TextView = findViewById(R.id.timeDifference)

		val timestamp = System.currentTimeMillis()

		localTime.text = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ROOT).format(timestamp)

		val greenwich = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ROOT)
		greenwich.timeZone = TimeZone.getTimeZone("GMT")
		greenwichTime.text = greenwich.format(timestamp)

		val difference = SimpleDateFormat("XXX", Locale.ROOT)
		timeDifference.text = difference.format(timestamp)
	}
}
