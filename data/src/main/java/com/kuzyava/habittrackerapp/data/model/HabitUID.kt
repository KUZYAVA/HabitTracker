package com.kuzyava.habittrackerapp.data.model

import com.squareup.moshi.Json

data class HabitUID(
    @Json(name = "uid") val uid: String
)