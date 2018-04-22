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
import presenter.build.BuildPresenter;
import presenter.build.BuildPresenterContract;
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
    private AsyncProcessIcon projectPlanLoadingIcon;
    private AsyncProcessIcon branchLoadingIcon;
    private JComboBox branchList;
    private AsyncProcessIcon buildLoadingIcon;
    private AsyncJButton runBtn;
    private JPanel detailsPanel;
    private JPanel buildPanel;
    private AsyncJButton stopBtn;
    private JTable buildsTable;
    private PlanPresenterContract planPresenter;
    private BuildPresenterContract buildPresenter;
    private BranchPresenterContract branchPresenter;
    private ItemListener planListMouseListener, branchListItemListener;
    private ActionListener runBtnListener;
    private ListSelectionListener buildSelectionListener;

    public static void main(String[] args) {
        IconFontSwing.register(FontAwesome.getIconFont());
        MainForm deviceFarm = new MainForm();
        JFrame frame = new JFrame("BambooPlugin");
        frame.setContentPane(deviceFarm.rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/4);
        frame.setVisible(true);
        deviceFarm.init();
    }

    void createUIComponents(){
        projectPlanLoadingIcon = new AsyncProcessIcon("loading...");
        branchLoadingIcon = new AsyncProcessIcon("loading...");
        buildLoadingIcon = new AsyncProcessIcon("loading...");
        branchLoadingIcon.setVisible(false);
        buildLoadingIcon.setVisible(false);
    }

    private void init() {
        planPresenter = new PlanPresenter(this);
        buildPresenter = new BuildPresenter(this);
        branchPresenter = new BranchPresenter(this, buildPresenter);
        planPresenter.loadPlan();
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
        if (presenter instanceof PlanPresenter) {
            return projectPlanLoadingIcon;
        }
        if (presenter instanceof BranchPresenter) {
            return branchLoadingIcon;
        }
        if (presenter instanceof BuildPresenter) {
            return buildLoadingIcon;
        }
        return null;
    }
}
