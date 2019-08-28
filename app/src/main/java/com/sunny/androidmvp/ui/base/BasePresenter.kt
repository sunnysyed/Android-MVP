package com.sunny.androidmvp.ui.base

abstract class BasePresenter<View : BaseView> protected constructor(protected var view: View?) {

    internal fun destroyView() {
        view = null
    }
}