package com.github.szymon2603.fixedlengthrecordseditor.recordseditor.ui

import com.intellij.ui.table.JBTable
import javax.swing.table.TableModel

class ListTable(model: TableModel) : JBTable(model) {

    constructor() : this(ListTableModel.EMPTY_MODEL)

    init {
        configureCellEditors()
    }

    private fun configureCellEditors() {
        if (model is ListTableModel<*>) {
            val customCellEditors = (model as ListTableModel<*>).getCustomCellEditors()
            if (customCellEditors.isNotEmpty()) {
                customCellEditors.forEach {
                    setDefaultEditor(it.first, it.second)
                }
            }
        }
    }

    fun updateModel(model: ListTableModel<*>) {
        setModel(model)
        configureCellEditors()
    }
}
