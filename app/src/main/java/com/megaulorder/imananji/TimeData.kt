package com.megaulorder.imananji

import com.google.gson.annotations.SerializedName

data class TimeData(

	@SerializedName("unixtime")
	val unixTime: Long,

	@SerializedName("timezone")
	val timeZone: String
)
