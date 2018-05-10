package presenter.auth

import persist.BambooPluginSettings
import models.project.Project
import network.AsyncTransformer
import network.HttpClient
import presenter.Presenter
import kotlinx.coroutines.experimental.swing.Swing as UI

class LoginPresenter : LoginPresenterContract{

    override fun testConnection(url : String, onStart : () -> Unit, onFinish : () -> Unit) {
        onStart.invoke()
        BambooPluginSettings.getInstance().state.url = url
        HttpClient.api.testConnection()
                .compose(AsyncTransformer<Any, Any>())
                .subscribe {
                    onFinish.invoke()
                }
    }

    override fun login(url : String, username: String, password: String, onStart: () -> Unit, onFinish : (Project) -> Unit) {
        onStart.invoke()
        BambooPluginSettings.getInstance().state.username = username
        BambooPluginSettings.getInstance().state.password = password
        BambooPluginSettings.getInstance().state.url = url
        HttpClient.api.login()
                .compose(AsyncTransformer<Project, Project>())
                .subscribe {
                    list ->
                    run {
                        //update Project list
                        // save login credentials
                        onFinish.invoke(list)
                    }
                }
    }

}

interface LoginPresenterContract : Presenter {

    fun login(url : String, username : String, password : String, onStart : () -> Unit, onFinish : (Project) -> Unit)

    fun testConnection(url : String, onStart : () -> Unit, onFinish : () -> Unit)
}