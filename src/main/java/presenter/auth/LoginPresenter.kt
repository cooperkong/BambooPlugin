package presenter.auth

import network.AsyncTransformer
import network.HttpClient
import presenter.Presenter
import ui.AsyncLoadUi

class LoginPresenter(val ui : LoginPresenterContract.UI) : LoginPresenterContract{

    private val api = HttpClient.api

    override fun testConnection(onStart : () -> Unit, onFinish : () -> Unit) {
        api.testConnection()
                .compose(AsyncTransformer<Any, Any>())
                .doOnSubscribe {
                    onStart.invoke()
                }
                .subscribe {
                    onFinish.invoke()
                }
    }

    override fun login(username: String, password: String, onStart: () -> Unit, onFinish: () -> Unit) {

    }

}

interface LoginPresenterContract : Presenter {

    fun login(username : String, password : String, onStart : () -> Unit, onFinish : () -> Unit)

    fun testConnection(onStart : () -> Unit, onFinish : () -> Unit)

    interface UI : AsyncLoadUi {
        fun login()
    }
}