import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import ui.BuildsForm

fun getBuildToolWindow(dataContext: DataContext) : BuildsForm? = getProjectComponent(getCurrentProject(dataContext), BuildsForm::class.java)


fun <T> getProjectComponent(project: Project?, clazz: Class<T>): T? {
    return if (project == null || project.isDisposed) {
        null
    } else clazz.cast(project.picoContainer.getComponentInstanceOfType(clazz))
}

fun getCurrentProject(dataContext: DataContext) = PlatformDataKeys.PROJECT.getData(dataContext)
