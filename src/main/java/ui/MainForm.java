package ui;

import com.intellij.ui.Gray;
import com.intellij.util.ui.AsyncProcessIcon;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;
import kotlin.Unit;
import models.branch.Branch;
import models.branch.BranchItem;
import models.expandedresult.Result;
import models.expandedresult.ResultItem;
import org.jetbrains.annotations.NotNull;
import presenter.branch.BranchPresenter;
import presenter.branch.BranchPresenterContract;
import presenter.builds.BuildPresenter;
import presenter.builds.BuildPresenterContract;
import presenter.plan.PlanPresenter;
import presenter.plan.PlanPresenterContract;
import ui.renderer.ListItemWithIconRender;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class MainForm implements PlanPresenterContract.UI, BranchPresenterContract.BranchUI, BuildPresenterContract.UI{
    private JComboBox planList;
    private JPanel rootPanel;
    private JComboBox branchList;
    private AsyncProcessIcon buildLoadingIcon;
    private AsyncJButton runBtn;
    private JPanel detailsPanel;
    private JPanel buildPanel;
    private JTable buildsTable;
    private AsyncJButton branchLoadingIcon2;
    private AsyncJButton projectPlanLoadingIcon2;
    private PlanPresenterContract planPresenter;
    private BuildPresenterContract buildPresenter;
    private BranchPresenterContract branchPresenter;
    private ItemListener planListMouseListener, branchListItemListener;
    private ActionListener runBtnListener;
    private ListSelectionListener buildSelectionListener;
    private ActionListener branchLoadingBtnActionListener;
    private String buildKey;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    void createUIComponents(){
        buildLoadingIcon = new AsyncProcessIcon("loading...");
        buildLoadingIcon.setVisible(false);
    }

    public void init(String projectKey) {
        planPresenter = new PlanPresenter(this);
        buildPresenter = new BuildPresenter(this);
        branchPresenter = new BranchPresenter(this, buildPresenter);
        planPresenter.loadPlan(projectKey);
        projectPlanLoadingIcon2.addActionListener( a -> planPresenter.loadPlan(projectKey));
        rootPanel.addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                // when user clicked on the tool window, should load builds list.
                if (buildKey != null) {
                    buildPresenter.loadBuilds(buildKey);
                }
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {

            }

            @Override
            public void ancestorMoved(AncestorEvent event) {

            }
        });
    }

    @Override
    public void showPlanList(@NotNull Result result) {
        planList.removeItemListener(planListMouseListener);
        planList.removeAllItems();
        planListMouseListener = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                branchPresenter.loadBranch(result.getResults().getResult().get(planList.getSelectedIndex()).getKey());
        };
        planList.addItemListener(planListMouseListener);
        for (ResultItem item : result.getResults().getResult()) {
            planList.addItem(item.getPlan().getShortName());
        }
        branchLoadingIcon2.removeActionListener(branchLoadingBtnActionListener);
        branchLoadingBtnActionListener = a -> branchPresenter.loadBranch(result.getResults().getResult().get(planList.getSelectedIndex()).getKey());
        branchLoadingIcon2.addActionListener(branchLoadingBtnActionListener);
    }

    @Override
    public void showBranchList(@NotNull Branch branch) {
        branchList.removeItemListener(branchListItemListener);
        branchList.removeAllItems();
        branchList.setRenderer(new ListItemWithIconRender(index -> IconFontSwing.buildIcon(FontAwesome.CODE_FORK, 16F, Gray._112)));
        branchListItemListener = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                buildKey = branch.getBranches().getBranch().get(branchList.getSelectedIndex()).getKey();
                buildPresenter.loadBuilds(buildKey);
            }
        };
        branchList.addItemListener(branchListItemListener);
        for (BranchItem item : branch.getBranches().getBranch()) {
            branchList.addItem(item.getShortName());
        }

        //add branch run listener
        runBtn.removeActionListener(runBtnListener);
        runBtnListener = e -> branchPresenter.startNewBuild(branch.getBranches().getBranch().get(branchList.getSelectedIndex()).getKey(),
                () -> {runBtn.startLoading(); return Unit.INSTANCE; },
                () -> {runBtn.stopLoading(); return Unit.INSTANCE; });
        runBtn.addActionListener(runBtnListener);
    }

    @Override
    public void showBranchResult(@NotNull Result result) {
            new BuildDetailTable(buildsTable, result).update();
    }

    @Override
    public void startLoadingPlan() {
        projectPlanLoadingIcon2.startLoading();
    }

    @Override
    public void stopLoadingPlan() {
        projectPlanLoadingIcon2.stopLoading();
    }

    @Override
    public void startLoadingBranch() {
        branchLoadingIcon2.startLoading();
    }

    @Override
    public void stopLoadingBranch() {
        branchLoadingIcon2.stopLoading();
    }

    @Override
    public void startLoadingBuilds() {
        buildLoadingIcon.resume();
        buildLoadingIcon.setVisible(true);
    }

    @Override
    public void stopLoadingBuilds() {
        buildLoadingIcon.suspend();
        buildLoadingIcon.setVisible(false);
    }
}
