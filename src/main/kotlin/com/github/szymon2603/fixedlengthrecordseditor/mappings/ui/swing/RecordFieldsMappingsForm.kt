package com.github.szymon2603.fixedlengthrecordseditor.mappings.ui.swing

import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldsMapping
import com.github.szymon2603.fixedlengthrecordseditor.mappings.ui.RecordFieldsMappingsFormModel
import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.ui.ListTable
import com.intellij.ui.ToolbarDecorator
import java.awt.BorderLayout
import java.util.UUID
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.ListSelectionModel
import javax.swing.event.TableModelEvent

class RecordFieldsMappingsForm(initialFormState: Map<UUID, RecordFieldsMapping>) {

    lateinit var mainPanel: JPanel

    lateinit var mappingsTablePanel: JPanel
    private val mappingsTable: ListTable

    lateinit var descriptorTablePanel: JPanel
    private val descriptorTable: ListTable

    private val formModel = RecordFieldsMappingsFormModel(initialFormState.toList())
    val state: Map<UUID, RecordFieldsMapping>
        get() = formModel.currentFormState

    init {
        // Descriptors table
        descriptorTable = ListTable(formModel.descriptorsTableModel)

        val descriptorTableDecorator = ToolbarDecorator.createDecorator(descriptorTable)
        descriptorTableDecorator.disableUpDownActions()
        descriptorTableDecorator.setAddAction { formModel.addDescriptor(descriptorTable.selectedRow) }
        descriptorTableDecorator.setRemoveAction { formModel.removeDescriptor(descriptorTable.selectedRow) }
        descriptorTablePanel.add(descriptorTableDecorator.createPanel(), BorderLayout.CENTER)

        descriptorTable.selectionModel.selectionMode = ListSelectionModel.SINGLE_SELECTION
        if (initialFormState.isNotEmpty() && initialFormState.toList()[0].second.fieldDescriptors.isNotEmpty()) {
            descriptorTable.changeSelection(0, 0, false, false)
        }

        // Mappings table
        mappingsTable = ListTable(formModel.mappingsTableModel)

        val mappingsTableDecorator = ToolbarDecorator.createDecorator(mappingsTable)
        mappingsTableDecorator.disableUpDownActions()
        mappingsTableDecorator.setAddAction { formModel.addMapping(mappingsTable.selectedRow) }
        mappingsTableDecorator.setRemoveAction() { formModel.removeMapping(mappingsTable.selectedRow) }
        mappingsTablePanel.add(mappingsTableDecorator.createPanel(), BorderLayout.CENTER)

        mappingsTable.selectionModel.selectionMode = ListSelectionModel.SINGLE_SELECTION
        if (initialFormState.isNotEmpty()) {
            mappingsTable.changeSelection(0, 0, false, false)
        }

        descriptorTable.model.addTableModelListener(createListenerToFixSelectionOnRowRemoval(descriptorTable))

        mappingsTable.model.addTableModelListener(createListenerToFixSelectionOnRowRemoval(mappingsTable))
        mappingsTable.model.addTableModelListener {
            if (it.type == TableModelEvent.INSERT) {
                descriptorTable.changeSelection(-1, 0, false, false)
            }
        }
        mappingsTable.selectionModel.addListSelectionListener {
            if (!it.valueIsAdjusting) {
                formModel.updateDescriptorsModelFor(mappingsTable.selectedRow)
                if (!formModel.isDescriptorListEmpty(mappingsTable.selectedRow)) {
                    descriptorTable.changeSelection(0, 0, false, false)
                } else {
                    descriptorTable.changeSelection(-1, 0, false, false)
                }
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
