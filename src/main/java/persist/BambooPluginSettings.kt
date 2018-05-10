package persist

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "bambooplugin",
        storages = [(Storage("bambooplugin.xml"))])
data class BambooPluginSettings(var url : String = "",
                                var username : String = "",
                                var password : String = "") : PersistentStateComponent<BambooPluginSettings> {

    override fun getState(): BambooPluginSettings = this

    override fun loadState(p0: BambooPluginSettings?) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance() = ServiceManager.getService(BambooPluginSettings::class.java)
    }
}