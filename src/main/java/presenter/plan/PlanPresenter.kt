package presenter.plan

import models.expandedresult.Result
import network.AsyncTransformer
import network.HttpClient
import presenter.Presenter
import ui.AsyncLoadUi

class PlanPresenter(private val ui : PlanPresenterContract.UI) : PlanPresenterContract {

    private val api = HttpClient.api

    override fun loadPlan(projectKey : String) {
        ui.startLoading(this)
        api.getResult(projectKey = projectKey, showStage = "")
                .compose(AsyncTransformer<Result, Result>())
                .subscribe( {
                    ui.stopLoading(this)
                    ui.showPlanList(it)
                })
    }

}

interface PlanPresenterContract : Presenter {
    interface UI : AsyncLoadUi{
        fun showPlanList(result: Result)
    }

    fun loadPlan(projectKey : String)
}