package com.sunny.androidmvp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity<Presenter : BasePresenter<*>> : AppCompatActivity() {

    lateinit var presenter: Presenter

    protected abstract fun createPresenter(): Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    public override fun onDestroy() {
        super.onDestroy()
        presenter.destroyView()
    }
}