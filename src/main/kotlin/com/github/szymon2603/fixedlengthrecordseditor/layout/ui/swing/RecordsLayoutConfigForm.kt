package com.github.szymon2603.fixedlengthrecordseditor.layout.ui.swing

import com.github.szymon2603.fixedlengthrecordseditor.layout.model.RecordMapping
import com.github.szymon2603.fixedlengthrecordseditor.layout.model.RecordsLayout
import com.github.szymon2603.fixedlengthrecordseditor.layout.ui.swing.table.RecordsLayoutColumns
import com.intellij.openapi.editor.SelectionModel
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.table.TableView
import com.intellij.util.ui.ListTableModel
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.ComboBoxModel
import javax.swing.DefaultComboBoxModel
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JPanel
import javax.swing.JRadioButton
import javax.swing.JTextField
import javax.swing.ListSelectionModel

class RecordsLayoutConfigForm(val layouts: MutableList<RecordsLayout>) {

    lateinit var mainPanel: JPanel
    lateinit var layoutsTablePanel: JPanel
    lateinit var fieldMappingsTablePanel: JPanel
    lateinit var mappingsComboBox: JComboBox<RecordMapping>
    lateinit var addMappingButton: JButton
    lateinit var removeMappingButton: JButton
    lateinit var mappingNameTextField: JTextField
    lateinit var allRecordsRadioButton: JRadioButton
    lateinit var recordsMatchesRadioButton: JRadioButton
    lateinit var recordsMatchesToTextField: JTextField
    lateinit var recordWithIndexRadioButton: JRadioButton
    lateinit var recordIndexTextField: JTextField

    init {
        createLayoutsTable()
    }

    private fun createLayoutsTable() {
        val layoutsTableModel = ListTableModel<RecordsLayout>(RecordsLayoutColumns.Name)
        layoutsTableModel.items = layouts

        val layoutsTable = TableView(layoutsTableModel)
        layoutsTable.selectionModel.selectionMode = ListSelectionModel.SINGLE_SELECTION
        layoutsTable.selectionModel.addListSelectionListener {
            if (layoutsTable.selectedRow > -1) {
                val layout = layouts[layoutsTable.selectedRow]
                val recordMappings = layout.recordMappings
                val mappingModel = DefaultComboBoxModel(recordMappings.toTypedArray())
                mappingsComboBox.model = mappingModel
                if(recordMappings.isNotEmpty()) mappingsComboBox.selectedItem = recordMappings.first()
            }
        }

        val layoutsTableDecorator = ToolbarDecorator.createDecorator(layoutsTable)
        layoutsTableDecorator.setAddAction {
            layoutsTableModel.addRow(RecordsLayout("New Layout"))
        }
        layoutsTableDecorator.setRemoveAction {
            layoutsTableModel.removeRow(layoutsTable.selectedRow)
        }
        layoutsTableDecorator.disableUpDownActions()
        layoutsTableDecorator.setPreferredSize(Dimension(100, -1))
        layoutsTablePanel.add(layoutsTableDecorator.createPanel(), BorderLayout.CENTER)
    }
}