package com.github.szymon2603.fixedlengthrecordseditor.mappings.ui

import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldsMapping
import com.github.szymon2603.fixedlengthrecordseditor.mappings.ui.swing.RecordFieldsMappingsForm
import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.ui.ListTable
import com.intellij.ui.SingleSelectionModel
import com.intellij.ui.ToolbarDecorator
import java.awt.BorderLayout
import java.util.UUID
import javax.swing.JComponent
import javax.swing.JTable
import javax.swing.event.TableModelEvent

class RecordFieldsMappingsFormController(initialFormState: Map<UUID, RecordFieldsMapping>) {
    private val form = RecordFieldsMappingsForm()
    private val mappingsTable: ListTable
    private val descriptorTable: ListTable
    private val formModel = RecordFieldsMappingsFormModel(initialFormState.toList())

    val mainComponent: JComponent
        get() = form.mainPanel
    val state: Map<UUID, RecordFieldsMapping>
        get() = formModel.currentFormState

    init {
        // Descriptors table
        descriptorTable = ListTable(formModel.descriptorsTableModel)

        val descriptorTableDecorator = ToolbarDecorator.createDecorator(descriptorTable)
        descriptorTableDecorator.disableUpDownActions()
        descriptorTableDecorator.setAddAction { formModel.addDescriptor() }
        descriptorTableDecorator.setRemoveAction { formModel.removeDescriptor() }
        form.descriptorTablePanel.add(descriptorTableDecorator.createPanel(), BorderLayout.CENTER)

        descriptorTable.selectionModel = SingleSelectionModel()
        descriptorTable.changeSelection(formModel.selectedDescriptor, 0, false, false)

        // Mappings table
        mappingsTable = ListTable(formModel.mappingsTableModel)

        val mappingsTableDecorator = ToolbarDecorator.createDecorator(mappingsTable)
        mappingsTableDecorator.disableUpDownActions()
        mappingsTableDecorator.setAddAction { formModel.addMapping() }
        mappingsTableDecorator.setRemoveAction() { formModel.removeMapping() }
        form.mappingsTablePanel.add(mappingsTableDecorator.createPanel(), BorderLayout.CENTER)

        mappingsTable.selectionModel = SingleSelectionModel()
        mappingsTable.changeSelection(formModel.selectedMapping, 0, false, false)

        descriptorTable.model.addTableModelListener(createListenerToFixSelectionOnRowRemoval(descriptorTable))
        descriptorTable.selectionModel.addListSelectionListener {
            if (!it.valueIsAdjusting || descriptorTable.selectedRow == -1) {
                formModel.selectedDescriptor = descriptorTable.selectedRow
            }
        }

        mappingsTable.model.addTableModelListener(createListenerToFixSelectionOnRowRemoval(mappingsTable))
        mappingsTable.selectionModel.addListSelectionListener {
            if (!it.valueIsAdjusting || mappingsTable.selectedRow == -1) {
                formModel.selectedMapping = mappingsTable.selectedRow
                descriptorTable.changeSelection(0, 0, false, false)
            }
        }
    }

    private fun createListenerToFixSelectionOnRowRemoval(table: JTable): (TableModelEvent) -> Unit {
        return {
            if (it.type == TableModelEvent.DELETE) {
                table.changeSelection(it.firstRow - 1, 0, false, false)
            }
        }
    }
}
