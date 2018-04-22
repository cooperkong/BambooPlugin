package presenter.auth

import network.AsyncTask
import network.HttpClient
import okhttp3.Credentials
import presenter.Presenter
import ui.AsyncLoadUi

class LoginPresenter(val ui : LoginPresenterContract.UI) : LoginPresenterContract{

    private val api = HttpClient.api

    override fun login(username: String, password: String) {
        ui.startLoading(this)
        AsyncTask.toAsyncWorker<Unit, Unit>( {api.testConnection(Credentials.basic(username, password)).blockingAwait()}
                , { _ ->
            run {
                ui.stopLoading(this)
            }
        }).execute()
    }

}

interface LoginPresenterContract : Presenter {

    fun login(username : String, password : String)

    interface UI : AsyncLoadUi {
        fun login()
    }
}