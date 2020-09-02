package com.github.szymon2603.fixedlengthrecordseditor.ui.swing;

import javax.swing.*;

public class FixedLengthRecordsForm {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JPanel recordsConfigTab;
    private JPanel recordsTableTab;
    private RecordsConfigForm recordsConfigForm;
    private RecordsTableForm recordsTableForm;
    private JTable configTable;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JPanel getRecordsConfigTab() {
        return recordsConfigTab;
    }

    public JPanel getRecordsTableTab() {
        return recordsTableTab;
    }

    public RecordsConfigForm getRecordsConfigForm() {
        return recordsConfigForm;
    }

    public RecordsTableForm getRecordsTableForm() {
        return recordsTableForm;
    }

    public JTable getConfigTable() {
        return configTable;
    }
}
