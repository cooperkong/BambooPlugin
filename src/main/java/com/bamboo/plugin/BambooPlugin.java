package com.bamboo.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import ui.MainForm;
import ui.auth.LogInForm;

public class BambooPlugin extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        new LogInForm().show();
    }
}
