package presenter.builds

import models.expandedresult.Result
import network.AsyncTransformer
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
        api.getResult(key)
                .compose(AsyncTransformer<Result, Result>())
                .subscribe {
                    buildResult = it
                    ui.stopLoading(this)
                    ui.showBranchResult(it)
                }
    }

    override fun stopBuild(key: String, onStart: () -> Unit, onFinish: () -> Unit) {
        onStart.invoke()
        api.stopBuildsOrJobs(key)
                .compose(AsyncTransformer<Any, Any>())
                .subscribe {
                    onFinish.invoke()
                }
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