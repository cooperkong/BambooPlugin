package ui.auth;

import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;
import kotlin.Unit;
import models.auth.BambooPluginSettings;
import presenter.auth.LoginPresenter;
import ui.AsyncJButton;
import ui.MainForm;
import util.DialogUtilKt;

import javax.swing.*;
import java.awt.*;

public class LogInForm {
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
        loginPresenter = new LoginPresenter();
        prefillCredential();
        
        
        testConnectionBtn.addActionListener(e ->
                        loginPresenter.testConnection(url.getText(),
                            () -> {testConnectionBtn.startLoading(loginPresenter); return Unit.INSTANCE;},
                            () -> {
                                // show a dialog showing test was successful
                                DialogUtilKt.showSuccessDialog(loginPanel);
                                testConnectionBtn.stopLoading(loginPresenter);
                                return Unit.INSTANCE;
                            }
                        ));

        loginBtn.addActionListener(e -> {
            loginPresenter.login(url.getText(), username.getText(), new String(password.getPassword()),
                    () -> {
                        loginBtn.startLoading(loginPresenter);
                        return Unit.INSTANCE;
                    },
                    () -> {
                        frame.getContentPane().removeAll();
                        MainForm form = new MainForm();
                        frame.setContentPane(form.getRootPanel());
                        frame.pack();
                        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
                        form.init();
                        return Unit.INSTANCE;
                    });
        });
    }

    private void prefillCredential() {
        url.setText(BambooPluginSettings.Companion.getInstance().getState().getUrl());
        username.setText(BambooPluginSettings.Companion.getInstance().getState().getUsername());
        password.setText(BambooPluginSettings.Companion.getInstance().getState().getPassword());
    }

    void createUIComponents(){

    }
}
