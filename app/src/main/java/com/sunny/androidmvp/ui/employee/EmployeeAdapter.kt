package com.sunny.androidmvp.ui.employee

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sunny.androidmvp.R
import com.sunny.androidmvp.data.models.Employee
import kotlinx.android.synthetic.main.item_employee.view.*


class EmployeeAdapter : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {

    private var employees: Array<Employee>? = null

    init {
        employees = emptyArray()
    }

    fun setEmployees(employees: Array<Employee>) {
        this.employees = employees
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_employee, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return employees?.size ?: 0
    }

    private fun getItem(position: Int): Employee? {
        return employees?.get(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val title: TextView = itemView.title
        private val name: TextView = itemView.name
        private val locations: TextView = itemView.locations

        fun bind(employee : Employee){
            title.text = employee.title
            name.text = employee.name
            locations.text = employee.locations.joinToString { it }
        }
    }
}