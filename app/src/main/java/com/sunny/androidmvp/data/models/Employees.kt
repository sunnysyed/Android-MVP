package com.sunny.androidmvp.data.models

import com.squareup.moshi.Json

data class Employees(
    @Json(name = "employees")
    val employees: Array<Employee>
)