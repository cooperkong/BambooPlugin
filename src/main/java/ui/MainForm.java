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
import presenter.IconPresenter;
import presenter.Presenter;
import presenter.branch.BranchPresenter;
import presenter.branch.BranchPresenterContract;
import presenter.builds.BuildPresenter;
import presenter.builds.BuildPresenterContract;
import presenter.plan.PlanPresenter;
import presenter.plan.PlanPresenterContract;
import ui.renderer.ListItemWithIconRender;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MainForm implements PlanPresenterContract.UI, BranchPresenterContract.BranchUI, BuildPresenterContract.UI{
    private JComboBox planList;
    private JPanel rootPanel;
//    private AsyncProcessIcon projectPlanLoadingIcon;
//    private AsyncProcessIcon branchLoadingIcon;
    private JComboBox branchList;
    private AsyncProcessIcon buildLoadingIcon;
    private AsyncJButton runBtn;
    private JPanel detailsPanel;
    private JPanel buildPanel;
    private AsyncJButton stopBtn;
    private JTable buildsTable;
    private AsyncJButton branchLoadingIcon2;
    private AsyncJButton projectPlanLoadingIcon2;
    private PlanPresenterContract planPresenter;
    private BuildPresenterContract buildPresenter;
    private BranchPresenterContract branchPresenter;
    private ItemListener planListMouseListener, branchListItemListener;
    private ActionListener runBtnListener;
    private ListSelectionListener buildSelectionListener;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    void createUIComponents(){
//        projectPlanLoadingIcon = new AsyncProcessIcon("loading...");
//        branchLoadingIcon = new AsyncProcessIcon("loading...");
        buildLoadingIcon = new AsyncProcessIcon("loading...");
//        branchLoadingIcon.setVisible(false);
        buildLoadingIcon.setVisible(false);
    }

    public void init(String projectKey) {
        planPresenter = new PlanPresenter(this);
        buildPresenter = new BuildPresenter(this);
        branchPresenter = new BranchPresenter(this, buildPresenter);
        planPresenter.loadPlan(projectKey);
    }

    @Override
    public void showPlanList(@NotNull Result result) {
        planList.removeItemListener(planListMouseListener);
        planList.removeAllItems();
        planListMouseListener = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                branchPresenter.loadBranch(result.getResults().getResult().get(planList.getSelectedIndex()).getKey())
                        .doOnSubscribe(disposable -> projectPlanLoadingIcon2.startLoading(null))
                        .subscribe(branch -> showBranchList(branch));
        };
        planList.addItemListener(planListMouseListener);
        for (ResultItem item : result.getResults().getResult()) {
            planList.addItem(item.getPlan().getShortName());
        }
    }

    @Override
    public void showBranchList(@NotNull Branch branch) {
        branchList.removeItemListener(branchListItemListener);
        branchList.removeAllItems();
        branchList.setRenderer(new ListItemWithIconRender(index -> IconFontSwing.buildIcon(FontAwesome.CODE_FORK, 16F, Gray._112)));
        branchListItemListener = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                buildPresenter.loadBuilds(branch.getBranches().getBranch().get(branchList.getSelectedIndex()).getKey());
        };
        branchList.addItemListener(branchListItemListener);
        for (BranchItem item : branch.getBranches().getBranch()) {
            branchList.addItem(item.getShortName());
        }

        //add branch run listener
        runBtn.removeActionListener(runBtnListener);
        runBtnListener = e -> branchPresenter.startNewBuild(branch.getBranches().getBranch().get(branchList.getSelectedIndex()).getKey(),
                () -> {runBtn.startLoading(branchPresenter); return Unit.INSTANCE; },
                () -> {runBtn.stopLoading(branchPresenter); return Unit.INSTANCE; });
        runBtn.addActionListener(runBtnListener);
    }

    @Override
    public void showBranchResult(@NotNull Result result) {
//        buildList.removeAll();
//
//        //build branch results table
//        if (result.getResults().getSize() > 0) {
            new BuildDetailTable(buildsTable, result).update();
//        } else {
//            buildsTable.removeAll();
//        }
//
//        DefaultListModel listModel = new DefaultListModel();
//        buildList.setCellRenderer(new ListItemWithIconRender((IconPresenter) buildPresenter));
//        for (ResultItem item : result.getResults().getResult()) {
//            listModel.addElement(" #" + item.getBuildNumber());
//        }
//        buildList.setModel(listModel);
//        buildList.removeListSelectionListener(buildSelectionListener);
//        buildSelectionListener = e -> {
//            if (buildList.getSelectedIndex() >= 0)
//                new BuildDetailUpdater(detailsPanel, buildPresenter)
//                        .update(result.getResults().getResult().get(buildList.getSelectedIndex()));
//        };
//        buildList.addListSelectionListener(buildSelectionListener);
    }

    @Override
    public void startLoading(@NotNull Presenter presenter) {
        getLoadingIcon(presenter).setVisible(true);
        getLoadingIcon(presenter).resume();
    }

    @Override
    public void stopLoading(@NotNull Presenter presenter) {
        getLoadingIcon(presenter).setVisible(false);
        getLoadingIcon(presenter).suspend();
    }

    private AsyncProcessIcon getLoadingIcon(Presenter presenter) {
        if (presenter instanceof BuildPresenter) {
            return buildLoadingIcon;
        }
        return null;
    }
}
