package com.megaulorder.imananji

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class DigitalClockView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

	var timeZone: String? = null

	init {
		context.theme.obtainStyledAttributes(attrs, R.styleable.DigitalClockView, 0, 0).apply {
			try {
				timeZone = getString(R.styleable.DigitalClockView_timezone)
			} finally {
				recycle()
			}
		}
	}
}
