package ui.auth;

import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import presenter.Presenter;
import presenter.auth.LoginPresenter;
import presenter.auth.LoginPresenterContract;
import ui.AsyncJButton;
import ui.MainForm;
import util.DialogUtilKt;

import javax.swing.*;
import java.awt.*;

public class LogInForm implements LoginPresenterContract.UI{
    private JTextField url;
    private JTextField username;
    private JPasswordField password;
    private AsyncJButton testConnectionBtn;
    private AsyncJButton loginBtn;
    private JPanel loginPanel;
    private LoginPresenter loginPresenter;
    private JFrame frame = new JFrame("BambooPlugin");
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    static {
        IconFontSwing.register(FontAwesome.getIconFont());
    }

    public void show() {
        frame.setContentPane(loginPanel);
        setupFrame();
        init();
    }

    private void setupFrame() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
    }

    private void init() {
        loginPresenter = new LoginPresenter(this);
        testConnectionBtn.addActionListener(e ->
                        loginPresenter.testConnection(
                            () -> {testConnectionBtn.startLoading(loginPresenter); return Unit.INSTANCE;},
                            () -> {
                                // show a dialog showing test was successful
                                DialogUtilKt.showSuccessDialog(loginPanel);
                                testConnectionBtn.stopLoading(loginPresenter);
                                return Unit.INSTANCE;
                            }
                        ));

        loginBtn.addActionListener(e -> {
            frame.getContentPane().removeAll();
            MainForm form = new MainForm();
            frame.setContentPane(form.getRootPanel());
            frame.pack();
            frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
            form.init();
        });
    }

    void createUIComponents(){

    }

    @Override
    public void login() {

    }

    @Override
    public void startLoading(@NotNull Presenter presenter) {

    }

    @Override
    public void stopLoading(@NotNull Presenter presenter) {

    }
}
