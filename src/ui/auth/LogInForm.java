package ui.auth;

import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;
import org.jetbrains.annotations.NotNull;
import presenter.Presenter;
import presenter.auth.LoginPresenter;
import presenter.auth.LoginPresenterContract;
import ui.AsyncJButton;
import ui.MainForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInForm implements LoginPresenterContract.UI{
    private JTextField url;
    private JTextField username;
    private JPasswordField password;
    private AsyncJButton testConnectionBtn;
    private AsyncJButton loginBtn;
    private JPanel loginPanel;
    private LoginPresenter loginPresenter;

    public static void main(String[] args) {
        IconFontSwing.register(FontAwesome.getIconFont());
        LogInForm logInForm = new LogInForm();
        JFrame frame = new JFrame("BambooPlugin");
        frame.setContentPane(logInForm.loginPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
        logInForm.init();
    }

    private void init() {
        loginPresenter = new LoginPresenter(this);
        testConnectionBtn.addActionListener(e -> loginPresenter.login(username.getText(), new String(password.getPassword())));
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
