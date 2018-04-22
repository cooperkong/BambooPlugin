package ui.auth;

import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;
import ui.AsyncJButton;
import ui.MainForm;

import javax.swing.*;
import java.awt.*;

public class LogInForm {
    private JTextField url;
    private JTextField username;
    private JPasswordField password;
    private AsyncJButton testConnectionBtn;
    private AsyncJButton loginBtn;
    private JPanel loginPanel;

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
    }
}
