package presenter.auth

import network.AsyncTask
import network.HttpClient
import presenter.Presenter
import ui.AsyncLoadUi

class LoginPresenter(val ui : LoginPresenterContract.UI) : LoginPresenterContract{

    override fun testConnection(onStart : () -> Unit, onFinish : () -> Unit) {
        onStart.invoke()
        AsyncTask.toAsyncWorker<Unit, Unit>( {api.testConnection().blockingAwait()}
                , { _ ->
            run {
                onFinish.invoke()
            }
        }).execute()
    }

    private val api = HttpClient.api

    override fun login(username: String, password: String, onStart: () -> Unit, onFinish: () -> Unit) {
        ui.startLoading(this)
        AsyncTask.toAsyncWorker<Unit, Unit>( {api.testConnection().blockingAwait()}
                , { _ ->
            run {
                ui.stopLoading(this)
            }
        }).execute()
    }

}

interface LoginPresenterContract : Presenter {

    fun login(username : String, password : String, onStart : () -> Unit, onFinish : () -> Unit)

    fun testConnection(onStart : () -> Unit, onFinish : () -> Unit)

    interface UI : AsyncLoadUi {
        fun login()
    }
}