package com.sunny.androidmvp.data.models


import com.squareup.moshi.Json

data class Employee(
    @Json(name = "locations")
    val locations: List<String>,
    @Json(name = "name")
    val name: String,
    @Json(name = "title")
    val title: String
)