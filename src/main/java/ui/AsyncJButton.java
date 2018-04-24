package ui;

import com.intellij.util.ui.AsyncProcessIcon;
import org.jetbrains.annotations.NotNull;
import presenter.Presenter;

import javax.swing.*;

public class AsyncJButton extends JButton implements AsyncLoadUi {

    private AsyncProcessIcon processIcon = new AsyncProcessIcon("loading");
    private Icon runIcon;
    private String text;

    @Override
    public void startLoading(@NotNull Presenter presenter) {
        runIcon = getIcon();
        text = getText();
        setText("");
        setIcon(null);
        add(processIcon);
    }

    @Override
    public void stopLoading(@NotNull Presenter presenter) {
        remove(processIcon);
        setIcon(runIcon);
        setText(text);
    }
}
