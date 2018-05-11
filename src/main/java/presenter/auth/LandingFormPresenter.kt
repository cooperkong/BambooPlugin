package presenter.auth

import io.reactivex.Single
import persist.BambooPluginSettings
import models.project.Project
import network.AsyncTransformer
import network.HttpClient
import presenter.Presenter
import kotlinx.coroutines.experimental.swing.Swing as UI

class LandingFormPresenter : LandingFormPresenterContract{

    val pageProjectsMap : MutableMap<Int, Project> = mutableMapOf()

    override fun getProjects(startIndex: Int, onStart: () -> Unit, onFinish: (Project) -> Unit): Single<Project> {
        onStart.invoke()
        return HttpClient.api.getProjects(startIndex)
                .compose(AsyncTransformer<Project, Project>())
                .doOnSuccess {
                    project ->
                    run {
                        //update Project list
                        // save login credentials
                        pageProjectsMap[startIndex % 25] = project
                        onFinish.invoke(project)
                    }
                }
    }

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
        saveToSettings(url, username, password)
        HttpClient.api.login()
                .compose(AsyncTransformer<Project, Project>())
                .subscribe {
                    list ->
                    run {
                        //update Project list
                        // save login credentials
                        pageProjectsMap[0] = list
                        onFinish.invoke(list)
                    }
                }
    }

    private fun saveToSettings(url: String, username: String, password: String) {
        BambooPluginSettings.getInstance().state.username = username
        BambooPluginSettings.getInstance().state.password = password
        BambooPluginSettings.getInstance().state.url = url
    }

}

interface LandingFormPresenterContract : Presenter {

    fun login(url : String, username : String, password : String, onStart : () -> Unit, onFinish : (Project) -> Unit)

    fun getProjects(startIndex : Int, onStart : () -> Unit, onFinish : (Project) -> Unit) : Single<Project>

    fun testConnection(url : String, onStart : () -> Unit, onFinish : () -> Unit)
}