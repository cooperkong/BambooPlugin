package ui

import models.IN_PROGRESS
import models.QUEUED
import models.expandedresult.ResultItem
import presenter.build.BuildPresenterContract
import javax.swing.JComponent
import javax.swing.JPanel


class BuildDetailUpdater(val panel: JPanel,
                         val buildPresenter: BuildPresenterContract) {

    fun update (resultItem: ResultItem) {
        val stopRunBtn = panel.findComponentById("stopBtn") as AsyncJButton

        stopRunBtn.isVisible = (resultItem.lifeCycleState == IN_PROGRESS
                || resultItem.lifeCycleState == QUEUED)

        //clear all listeners
        stopRunBtn.actionListeners.map {
            stopRunBtn.removeActionListener(it)
        }

        val onFinish = {
            stopRunBtn.stopLoading(buildPresenter)
            buildPresenter.loadBuilds(resultItem.plan.planKey.key)
        }

        // reload build list after build is stopped
        stopRunBtn.addActionListener({ buildPresenter.stopBuild(resultItem.key,
                { stopRunBtn.startLoading(buildPresenter) }, onFinish) })
    }

    fun JPanel.findComponentById(name : String) : JComponent?  {
        val jComponents = ArrayList<JComponent>()
        for (c in components) {
            if (c is JComponent) {
                jComponents.add(c)
            }
        }
        return jComponents.find { component -> component.getClientProperty("id") == name }
    }
}