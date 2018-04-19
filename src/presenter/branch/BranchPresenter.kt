package presenter.branch

import models.branch.Branch
import network.AsyncTask
import network.HttpClient
import presenter.Presenter
import presenter.build.BuildPresenterContract
import ui.AsyncLoadUi

class BranchPresenter(val ui : BranchPresenterContract.BranchUI,
                      val buildPresenter: BuildPresenterContract) : BranchPresenterContract {
    private val api = HttpClient.api

    override fun startNewBuild(key: String, onStart : () -> Unit, onFinish : () -> Unit) {
        onStart.invoke()
        AsyncTask.toAsyncWorker<Unit, Unit>( {api.queueBuild(key).blockingAwait()}
                , { _ ->
            run {
                onFinish.invoke()
                buildPresenter.loadBuilds(key)
            }
        }).execute()
    }

    override fun loadBranch(key: String) {
        ui.startLoading(this)
        AsyncTask.toAsyncWorker<Branch, Branch>( {api.getProjectPlanBranches(key).blockingFirst()}
                , {data ->
            run {
                ui.stopLoading(this)
                ui.showBranchList(data)
            }
        }).execute()
    }
}

interface BranchPresenterContract : Presenter {

    fun startNewBuild(key: String, onStart: () -> Unit, onFinish: () -> Unit)

    fun loadBranch(key : String)

    interface BranchUI : AsyncLoadUi {
        fun showBranchList(branch: Branch)

    }
}