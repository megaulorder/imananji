package com.megaulorder.imananji.digital

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.megaulorder.imananji.R

/**
 * A view with a string attribute for timezone, e.g. "GMT+9", "Asia/Tokyo"
 */
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
