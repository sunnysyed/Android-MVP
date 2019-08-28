package com.sunny.androidmvp.ui.employee

import com.sunny.androidmvp.data.models.Employee
import com.sunny.androidmvp.ui.base.BaseView

interface EmployeeView : BaseView {
    fun showLoading()
    fun showEmployees(employees: Array<Employee>)
    fun showErrorMessage(error: Throwable)
    fun showLocationFilter(locations: Array<String>, locationFilters: BooleanArray)
}
