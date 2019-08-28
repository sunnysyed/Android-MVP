package com.sunny.androidmvp.ui.employee

import com.sunny.androidmvp.data.api.MockEmployeeClient
import com.sunny.androidmvp.data.models.Employee
import com.sunny.androidmvp.data.models.Employees
import com.sunny.androidmvp.ui.base.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeePresenter(view: EmployeeView, private val employeeClient: MockEmployeeClient) : BasePresenter<EmployeeView>(view) {

    var employees : Array<Employee>? = null
    var locationFilters : HashMap<String,Boolean> = HashMap()

    fun getEmployees() {
        if (employees == null) {
            view?.showLoading()
            employeeClient.api.getEmployees().enqueue(object : Callback<Employees> {
                override fun onFailure(call: Call<Employees>, t: Throwable) {
                    view?.showErrorMessage(t)
                }

                override fun onResponse(call: Call<Employees>, response: Response<Employees>) {
                    employees = response.body()?.employees?.sortedBy { it.name }?.toTypedArray()
                    val locations = employees?.flatMap { it.locations }?.distinct()?.toTypedArray()
                    locations?.forEach {
                        locationFilters[it] = true
                    }

                    view?.showEmployees(employees ?: emptyArray())
                }

            })
        }else{
            view?.showEmployees(employees ?: emptyArray())
        }
    }

    fun search(query: String) {
        if (query.isBlank()){
            view?.showEmployees(employees?.filter { employee -> shouldShow(employee.locations) }?.distinct()?.toTypedArray() ?: emptyArray())
        }else {
            view?.showEmployees(employees?.filter { employee -> shouldShow(employee.locations)  && (employee.name.contains(query, true) || employee.title.contains(query, true)) }?.toTypedArray() ?: emptyArray())
        }
    }

    fun onFilterTap() {
        view?.showLocationFilter(locationFilters.map { it.key }.toTypedArray(), locationFilters.map { it.value }.toBooleanArray())
    }

    fun updateLocationFilter(which: String, checked: Boolean) {
        locationFilters[which] = checked
        view?.showEmployees(employees?.filter { employee -> shouldShow(employee.locations) }?.distinct()?.toTypedArray() ?: emptyArray())
    }

    private fun shouldShow(locations: List<String>): Boolean {
        locations.forEach {
            if (locationFilters[it] == true){
                return true
            }
        }
        return false
    }
}