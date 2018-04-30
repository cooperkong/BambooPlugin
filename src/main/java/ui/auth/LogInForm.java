package ui.auth;

import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;
import kotlin.Unit;
import models.auth.BambooPluginSettings;
import models.project.Project;
import models.project.ProjectItem;
import org.jetbrains.annotations.NotNull;
import presenter.auth.LoginPresenter;
import ui.AsyncJButton;
import ui.paginatedTable.ObjectTableModel;
import ui.paginatedTable.PaginatedTableDecorator;
import ui.paginatedTable.PaginationDataProvider;
import util.DialogUtilKt;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;

public class LogInForm {
    private JTextField url;
    private JTextField username;
    private JPasswordField password;
    private AsyncJButton testConnectionBtn;
    private AsyncJButton loginBtn;
    private JPanel rootPanel;
    private JTable projectTable;
    private JPanel projectPanel;
    private JPanel loginPanel;
    private LoginPresenter loginPresenter;
    private JFrame frame = new JFrame("BambooPlugin");
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    static {
        IconFontSwing.register(FontAwesome.getIconFont());
    }

    public void show() {
        frame.setContentPane(rootPanel);
        setupFrame();
        init();
    }

    private void setupFrame() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.revalidate();
        frame.pack();
        frame.repaint();
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
                                DialogUtilKt.showSuccessDialog(rootPanel);
                                testConnectionBtn.stopLoading(loginPresenter);
                                return Unit.INSTANCE;
                            }
                        ));

        loginBtn.addActionListener(e -> {
            loginPresenter.login(url.getText(), username.getText(), new String(password.getPassword()),
                    () -> {
                        projectPanel.setVisible(false);
                        loginBtn.startLoading(loginPresenter);
                        return Unit.INSTANCE;
                    },
                    project -> {
//                        frame.getContentPane().removeAll();
//                        MainForm form = new MainForm();
//                        frame.setContentPane(form.getRootPanel());
                        projectPanel.setVisible(true);
                        loginBtn.stopLoading(loginPresenter);
                        initTable(project);
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

    private void initTable(Project project) {
        projectTable.setModel(createProjectModel(project.getProjects().getProject()));
        PaginatedTableDecorator.Companion.decorate(projectPanel, projectTable, createDataProvider(project)
                , new int[]{5, 10, 15, 20, 25}, 25);
        rootPanel.setPreferredSize(new Dimension(rootPanel.getPreferredSize().width
                , projectPanel.getPreferredSize().height + loginPanel.getPreferredSize().height));
        setupFrame();
    }

    private PaginationDataProvider<ProjectItem> createDataProvider(Project project) {
        return new PaginationDataProvider<ProjectItem>() {
            @Override
            public int getTotalRowCount() {
                return project.getProjects().getSize();
            }

            @NotNull
            @Override
            public List<ProjectItem> getRows(int startIndex, int endIndex) {
                return project.getProjects().getProject().subList(startIndex, endIndex);
            }
        };
    }

    private TableModel createProjectModel(List<ProjectItem> project) {
        ObjectTableModel tableModel = new ObjectTableModel<ProjectItem>() {
            @NotNull
            @Override
            public Object getValueAt(ProjectItem projectItem, int columnIndex) {
                switch (columnIndex) {
                    case 0 : return projectItem.getName();
                    case 1 : return projectItem.getKey();
                    case 2 : return projectItem.getDescription();
                }
                return "";
            }

            @Override
            public int getColumnCount() {
                return 3;
            }

            @NotNull
            @Override
            public String getColumnName(int column) {
                switch (column) {
                    case 0 : return "Project";
                    case 1 : return "Key";
                    case 2 : return "Description";
                }
                return "";
            }
        };
        return tableModel;
    }


}
