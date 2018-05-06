package presenter.branch

import io.reactivex.Observable
import models.branch.Branch
import network.AsyncTransformer
import network.HttpClient
import presenter.Presenter
import presenter.builds.BuildPresenterContract
import ui.AsyncLoadUi

class BranchPresenter(val ui : BranchPresenterContract.BranchUI,
                      private val buildPresenter: BuildPresenterContract) : BranchPresenterContract {
    private val api = HttpClient.api

    override fun startNewBuild(key: String, onStart : () -> Unit, onFinish : () -> Unit) {
        onStart.invoke()
        api.queueBuild(key)
                .compose(AsyncTransformer<Any, Any>())
                .subscribe {
                    onFinish.invoke()
                    buildPresenter.loadBuilds(key)
                }
    }

    override fun loadBranch(key: String) : Observable<Branch> = api.getProjectPlanBranches(key)
            .compose(AsyncTransformer<Branch, Branch>())
}

interface BranchPresenterContract : Presenter {

    fun startNewBuild(key: String, onStart: () -> Unit, onFinish: () -> Unit)

    fun loadBranch(key : String) : Observable<Branch>

    interface BranchUI : AsyncLoadUi {
        fun showBranchList(branch: Branch)
    }
}