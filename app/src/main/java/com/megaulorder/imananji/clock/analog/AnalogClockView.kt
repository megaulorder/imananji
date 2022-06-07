package com.megaulorder.imananji.clock.analog

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import com.megaulorder.imananji.R
import java.lang.StrictMath.toRadians
import kotlin.math.cos
import kotlin.math.sin

class AnalogClockView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

	var timeZone: String? = null

	var hours: Int = 9
	var minutes: Int = 0
	var seconds: Int = 0

	private val hoursHandRatio = 0.65f
	private val minutesHandRatio = 0.9f

	private var primaryColor: Int = Color.BLACK
	private var secondaryColor: Int = Color.WHITE

	private var borderWidth: Float = 10f

	init {
		context.theme.obtainStyledAttributes(attrs, R.styleable.AnalogClockView, 0, 0).apply {
			try {
				timeZone = getString(R.styleable.AnalogClockView_timezone)

				primaryColor = getColor(R.styleable.AnalogClockView_primaryColor, primaryColor)
				secondaryColor =
					getColor(R.styleable.AnalogClockView_secondaryColor, secondaryColor)

				borderWidth = getFloat(R.styleable.AnalogClockView_faceBorderWidth, borderWidth)
			} finally {
				recycle()
			}
		}
	}

	private val primaryPaint = Paint().apply {
		color = primaryColor
	}

	private val secondaryPaint = Paint().apply {
		color = secondaryColor
	}

	private val hourHandPaint = Paint().apply {
		color = primaryColor
		strokeWidth = borderWidth
	}

	private val minuteHandPaint = Paint().apply {
		color = primaryColor
		strokeWidth = borderWidth / 2
	}

	private var diameter: Int = 0
	private var radius: Float = 0f
	private var center = PointF()

	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec)
		val width = MeasureSpec.getSize(widthMeasureSpec)
		val height = MeasureSpec.getSize(heightMeasureSpec)
		diameter = if (width == 0 && height == 0) {
			0
		} else {
			if (width > height) height else width
		}
		radius = diameter / 2f
		center.x = width / 2f
		center.y = height / 2f

		setMeasuredDimension(width, height)
	}

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)
		drawFace(canvas)

		drawNumbers(canvas, primaryPaint)

		drawHours(canvas, hourHandPaint, hours, minutes)
		drawMinutesOrSeconds(canvas, minuteHandPaint, minutes, minutesHandRatio)
		drawMinutesOrSeconds(canvas, primaryPaint, seconds)

		postInvalidateDelayed(1000)
	}

	private fun drawHours(
		canvas: Canvas,
		paint: Paint,
		hours: Int,
		minutes: Int
	) {
		val stepByHour: Double = toRadians(30.0) // hour hand moves 30° with every full hour
		val stepByMinute: Double = toRadians(0.5) // hour hand moves 0.5° with every minute
		val length: Float = radius * hoursHandRatio
		val stopX: Double = center.x + sin(stepByHour * hours + stepByMinute * minutes) * length
		val stopY: Double = center.y - cos(stepByHour * hours + stepByMinute * minutes) * length

		canvas.drawLine(center.x, center.y, stopX.toFloat(), stopY.toFloat(), paint)
	}

	private fun drawMinutesOrSeconds(
		canvas: Canvas,
		paint: Paint,
		time: Int,
		ratio: Float = 1f,
	) {
		val step: Double = toRadians(6.0) // minute/second hands move by 6° with every minute/second
		val length: Float = radius * ratio
		val stopX: Double = center.x + sin(step * time) * length
		val stopY: Double = center.y - cos(step * time) * length

		canvas.drawLine(center.x, center.y, stopX.toFloat(), stopY.toFloat(), paint)
	}

	private fun drawNumbers(canvas: Canvas, paint: Paint) {
		for (i in (1..12)) {
			val angle = i * 30.0
			val radians = toRadians(angle)

			val sin = sin(radians)
			val cos = cos(radians)

			val offset = 0.9

			canvas.drawCircle(
				(center.x + radius * offset * sin).toFloat(),
				(center.y - radius * offset * cos).toFloat(),
				borderWidth / 2,
				paint
			)
		}
	}

	private fun drawFace(canvas: Canvas) {
		canvas.drawCircle(center.x, center.y, radius, primaryPaint)
		canvas.drawCircle(center.x, center.y, radius - borderWidth, secondaryPaint)
	}
}
