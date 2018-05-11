package ui.auth;

import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;
import kotlin.Unit;
import models.project.Project;
import models.project.ProjectItem;
import org.jetbrains.annotations.NotNull;
import persist.BambooPluginSettings;
import presenter.auth.LandingFormPresenter;
import ui.AsyncJButton;
import ui.MainForm;
import ui.paginatedTable.ObjectTableModel;
import ui.paginatedTable.PaginatedTableDecorator;
import ui.paginatedTable.PaginationDataProvider;
import util.DialogUtilKt;

import javax.swing.*;
import javax.swing.table.TableModel;
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
    private LandingFormPresenter loginPresenter;
    private PaginatedTableDecorator decorator;
    static {
        IconFontSwing.register(FontAwesome.getIconFont());
    }

    public JPanel show() {
        init();
        return rootPanel;
    }

    private void init() {
        loginPresenter = new LandingFormPresenter();
        prefillCredential();

        testConnectionBtn.addActionListener(e ->
                        loginPresenter.testConnection(url.getText(),
                            () -> {testConnectionBtn.startLoading(); return Unit.INSTANCE;},
                            () -> {
                                // show a dialog showing test was successful
                                DialogUtilKt.showSuccessDialog(rootPanel);
                                testConnectionBtn.stopLoading();
                                return Unit.INSTANCE;
                            }
                        ));

        loginBtn.addActionListener(e -> {
            loginPresenter.login(url.getText(), username.getText(), new String(password.getPassword()),
                    () -> {
                        projectPanel.setVisible(false);
                        loginBtn.startLoading();
                        return Unit.INSTANCE;
                    },
                    project -> {
                        projectPanel.setVisible(true);
                        loginBtn.stopLoading();
                        initTable(project);
                        return Unit.INSTANCE;
                    });
        });
    }

    private void prefillCredential() {
        url.setText(BambooPluginSettings.getInstance().getState().url);
        username.setText(BambooPluginSettings.getInstance().getState().username);
        password.setText(BambooPluginSettings.getInstance().getState().password);
    }

    void createUIComponents(){

    }

    private void initTable(Project project) {
        projectTable.setModel(createProjectModel(project.getProjects().getProject()));
        if (decorator == null) {
            decorator = PaginatedTableDecorator.Companion.<ProjectItem>decorate(projectPanel, projectTable, 25
                    , (currentPage) -> {
                        loginPresenter.getProjects(currentPage,
                                () -> Unit.INSTANCE,
                                nextProject -> {
                                    initTable(nextProject);
                                    return Unit.INSTANCE;
                                }).subscribe();
                        return Unit.INSTANCE;
                    });
        }
        decorator.init(createDataProvider(project));
        decorator.paginate();
        decorator.setOnProjectSelected(row -> {
            openPlanForm(project, (Integer) row);
            return Unit.INSTANCE;
        });
    }

    private Unit openPlanForm(Project project, Integer row) {
        // open build plan and builds form
        rootPanel.removeAll();
        MainForm mainForm = new MainForm();
        rootPanel.add(mainForm.getRootPanel());
        mainForm.init(project.getProjects().getProject().get(row).getKey());
        return Unit.INSTANCE;
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
                // call api to fetch next 25 items
                return project.getProjects().getProject().subList(0, 24);
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
