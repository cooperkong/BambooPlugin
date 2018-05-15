package action

import com.bamboo.plugin.BambooPluginToolWindow
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class BackToLandingAction : AnAction(AllIcons.Actions.Back) {

    override fun actionPerformed(e: AnActionEvent?) {
        BambooPluginToolWindow.instance.showLandingForm()
    }

}