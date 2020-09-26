package com.github.szymon2603.fixedlengthrecordseditor.mappings.ui

import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldsMapping
import com.github.szymon2603.fixedlengthrecordseditor.mappings.ui.swing.RecordFieldsMappingsForm
import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.ui.ListTable
import com.intellij.ui.SingleSelectionModel
import com.intellij.ui.ToolbarDecorator
import java.awt.BorderLayout
import java.util.UUID
import javax.swing.JComponent

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
        descriptorTable.selectionModel = SingleSelectionModel()
        descriptorTable.changeSelection(0, 0, false, false)

        val descriptorTableDecorator = ToolbarDecorator.createDecorator(descriptorTable)
        descriptorTableDecorator.disableUpDownActions()
        descriptorTableDecorator.setAddAction { formModel.addDescriptor() }
        descriptorTableDecorator.setRemoveAction { formModel.removeDescriptor() }
        form.descriptorTablePanel.add(descriptorTableDecorator.createPanel(), BorderLayout.CENTER)

        // Mappings table
        mappingsTable = ListTable(formModel.mappingsTableModel)
        mappingsTable.selectionModel = SingleSelectionModel()
        mappingsTable.changeSelection(0, 0, false, false)

        val mappingsTableDecorator = ToolbarDecorator.createDecorator(mappingsTable)
        mappingsTableDecorator.disableUpDownActions()
        mappingsTableDecorator.setAddAction { formModel.addMapping() }
        mappingsTableDecorator.setRemoveAction() { formModel.removeMapping() }
        form.mappingsTablePanel.add(mappingsTableDecorator.createPanel(), BorderLayout.CENTER)

        descriptorTable.selectionModel.addListSelectionListener {
            if (!it.valueIsAdjusting) {
                formModel.selectedDescriptor = descriptorTable.selectedRow
            }
        }
        mappingsTable.selectionModel.addListSelectionListener {
            if (!it.valueIsAdjusting) {
                formModel.selectedMapping = mappingsTable.selectedRow
                descriptorTable.changeSelection(0, 0, false, false)
            }
        }
    }
}
