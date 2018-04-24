package ui

import presenter.Presenter

interface AsyncLoadUi {
    fun startLoading(presenter: Presenter)
    fun stopLoading(presenter: Presenter)
}