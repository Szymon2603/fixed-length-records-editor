package com.github.szymon2603.fixedlengthrecordseditor.recordseditor.ui

import com.intellij.ui.table.JBTable
import javax.swing.table.TableModel

class ListTable(model: TableModel) : JBTable(model) {

    init {
        if (model is ListTableModel<*>) {
            val customCellEditors = model.getCustomCellEditors()
            if (customCellEditors.isNotEmpty()) {
                customCellEditors.forEach {
                    setDefaultEditor(it.first, it.second)
                }
            }
        }
    }
}
