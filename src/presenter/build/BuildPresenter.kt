package presenter.build

import models.expandedresult.Result
import network.AsyncTask
import network.HttpClient
import presenter.IconPresenter
import presenter.Presenter
import ui.AsyncLoadUi
import util.IconUtil.Companion.getIcon
import javax.swing.Icon

class BuildPresenter(val ui : BuildPresenterContract.UI) : BuildPresenterContract, IconPresenter {

    private val api = HttpClient.api
    lateinit var buildResult : Result

    override fun loadBuilds(key: String) {
        ui.startLoading(this)
        AsyncTask.toAsyncWorker<Result, Result>( {api.getResult(key).blockingFirst()}
                , {data ->
            run {
                buildResult = data
                ui.stopLoading(this)
                ui.showBranchResult(data)
            }
        }).execute()
    }

    override fun stopBuild(key: String, onStart: () -> Unit, onFinish: () -> Unit) {
        onStart.invoke()
        AsyncTask.toAsyncWorker<Unit, Unit>(
                {
                    api.stopBuildsOrJobs(key).blockingAwait()
                }
                , { _ ->
                    run {
                        onFinish.invoke()
                    }
                }).execute()
    }

    override fun getIcon(index: Int): Icon = getIcon(buildResult.results.result!![index].lifeCycleState, buildResult.results.result!![index].buildState)
}

interface BuildPresenterContract : Presenter {

    fun loadBuilds(key : String)

    fun stopBuild(key : String, onStart: () -> Unit, onFinish: () -> Unit)

    interface UI : AsyncLoadUi {
        fun showBranchResult(result: Result)
    }
}