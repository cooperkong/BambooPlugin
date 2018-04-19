package com.bamboo.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import ui.MainForm;

public class BambooPlugin extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        MainForm.main(null);
    }
}
