package com.bamboo.plugin

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import ui.auth.LandingForm

class BambooPluginToolWindow : ToolWindowFactory {

    private object Holder {
        var toolWindow : ToolWindow? = null
        val INSTANCE = BambooPluginToolWindow()
    }

    companion object {
        val instance: BambooPluginToolWindow by lazy { Holder.INSTANCE }
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        Holder.toolWindow = toolWindow
        showLandingForm(toolWindow)
    }

    override fun shouldBeAvailable(project: Project) = true

    override fun isDoNotActivateOnStart() = true

    fun showLandingForm(toolWindow: ToolWindow? = Holder.toolWindow) {
        val content = ContentFactory.SERVICE.getInstance().createContent(LandingForm().show(), "", false)
        toolWindow?.contentManager?.removeAllContents(true)
        toolWindow?.contentManager?.addContent(content)
    }
}
