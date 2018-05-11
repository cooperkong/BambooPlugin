package persist;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;


@State(name = "bambooplugin",
        storages = @Storage(file = "bambooplugin.xml"))
public class BambooPluginSettings implements PersistentStateComponent<BambooPluginSettings> {

    public String url;
    public String username;
    public String password;

    @NotNull
    @Override
    public BambooPluginSettings getState() {
        return this;
    }

    @Override
    public void loadState(BambooPluginSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public static BambooPluginSettings getInstance() {
        return ServiceManager.getService(BambooPluginSettings.class);
    }
}