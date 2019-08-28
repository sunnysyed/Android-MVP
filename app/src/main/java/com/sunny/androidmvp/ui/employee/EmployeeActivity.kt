package com.sunny.androidmvp.ui.employee

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.androidmvp.R
import com.sunny.androidmvp.data.api.MockEmployeeClient
import com.sunny.androidmvp.data.models.Employee
import com.sunny.androidmvp.ui.base.BaseActivity
import com.sunny.androidmvp.util.SimpleDividerItemDecoration
import kotlinx.android.synthetic.main.activity_employees.*


class EmployeeActivity : BaseActivity<EmployeePresenter>(), EmployeeView {

    val employeeAdapter = EmployeeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employees)
        title = getString(R.string.employees)
        recyclerView.adapter = employeeAdapter
        recyclerView.addItemDecoration( SimpleDividerItemDecoration(this))
        recyclerView.layoutManager = LinearLayoutManager(this)
        presenter.getEmployees()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem =  menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as? SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.search(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.search(newText)
                return true
            }

        })
        menu.findItem(R.id.action_filter).setOnMenuItemClickListener {
            presenter.onFilterTap()
            return@setOnMenuItemClickListener true
        }
        return true
    }

    override fun createPresenter(): EmployeePresenter {
        return EmployeePresenter(this, MockEmployeeClient)
    }

    override fun showLoading() {
        recyclerView.visibility = View.GONE
        loadingView.visibility = View.VISIBLE
    }

    override fun showEmployees(employees: Array<Employee>) {
        recyclerView.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        employeeAdapter.setEmployees(employees)
    }

    override fun showErrorMessage(error: Throwable) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun showLocationFilter(
        locations: Array<String>,
        locationFilters: BooleanArray
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.locations))
        builder.setMultiChoiceItems(locations, locationFilters
        ) { _, which, isChecked ->
            presenter.updateLocationFilter(locations[which], isChecked)
        }

        val dialog = builder.create()
        dialog.show()
    }
}
