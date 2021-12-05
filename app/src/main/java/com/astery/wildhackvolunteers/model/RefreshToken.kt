package com.astery.wildhack.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RefreshToken(@Json(name = "refresh_token") val refreshToken: String)