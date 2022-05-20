package com.megaulorder.imananji

import android.widget.TextView

class TimeDifferenceWidget(
	private val view: TextView
) {

	fun setText(text: String) {
		view.text = text.replaceFirst(':', '時')
			.replaceFirst(':', '分')
			.replaceFirst(':', '秒')
			.replace(Regex("00."), "")
	}
}
