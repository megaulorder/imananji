package com.megaulorder.imananji

import android.widget.TextView
import kotlin.time.Duration

class TimeDifferenceWidget(
	private val view: TextView
) {

	fun setText(text: Duration) {
		view.text = text.toString().replace('h', '時').replace('m', '分').replace('s', '秒')
	}
}
