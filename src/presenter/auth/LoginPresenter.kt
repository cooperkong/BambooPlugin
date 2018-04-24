package presenter.auth

import network.AsyncTask
import network.AsyncTranformer
import network.HttpClient
import presenter.Presenter
import ui.AsyncLoadUi

class LoginPresenter(val ui : LoginPresenterContract.UI) : LoginPresenterContract{

    override fun testConnection(onStart : () -> Unit, onFinish : () -> Unit) {
        api.testConnection()
                .compose(AsyncTranformer)
                .doOnSubscribe {
                    onStart.invoke()
                }
                .subscribe {
                    onFinish.invoke()
                }
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