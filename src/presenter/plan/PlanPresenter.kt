package presenter.plan

import models.expandedresult.Result
import network.AsyncTask
import network.HttpClient
import presenter.Presenter
import ui.AsyncLoadUi

class PlanPresenter(private val ui : PlanPresenterContract.UI) : PlanPresenterContract {

    private val api = HttpClient.api

    override fun loadPlan() {
        ui.startLoading(this)
        AsyncTask.toAsyncWorker<Result, Result>( {api.getResult(showStage = "").blockingFirst()}
        , {data ->
            run {
                ui.stopLoading(this)
                ui.showPlanList(data)
            }
        }).execute()
    }

}

interface PlanPresenterContract : Presenter {
    interface UI : AsyncLoadUi{
        fun showPlanList(result: Result)
    }

    fun loadPlan()
}