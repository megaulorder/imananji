package com.megaulorder.imananji.button

import android.widget.Button
import com.megaulorder.imananji.R

class SwitchButtonWidget(
	private val view: Button
) {

	var clickListener: (() -> Unit)? = null

	init {
		view.setOnClickListener { clickListener?.invoke() }
	}

	fun showDigital() {
		view.text = view.context.getText(R.string.digital)
	}

	fun showAnalog() {
		view.text = view.context.getText(R.string.analog)
	}
}
