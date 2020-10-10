package com.github.szymon2603.fixedlengthrecordseditor.recordseditor.ui.swing

import com.github.szymon2603.fixedlengthrecordseditor.mappings.components.RecordFieldsMappingsStore
import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldDescriptor
import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldsMapping
import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.model.Field
import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.model.Record
import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.ui.Column
import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.ui.FixedLengthRecordsEditor
import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.ui.ListTable
import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.ui.ListTableModel
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.ToolbarDecorator
import org.slf4j.LoggerFactory
import java.awt.BorderLayout
import java.util.UUID
import javax.swing.DefaultComboBoxModel
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JComponent
import javax.swing.JPanel

class RecordsEditorForm(project: Project, openedFile: VirtualFile) : FixedLengthRecordsEditor(project, openedFile) {

    lateinit var mainPanel: JPanel
    lateinit var recordsTablePanel: JPanel
    lateinit var noMappingSelectedPanel: JPanel
    lateinit var applyMappingButton: JButton
    lateinit var mappingComboBox: JComboBox<MappingItem>

    private val selectedMappingItem: MappingItem
        get() = mappingComboBox.selectedItem as MappingItem

    private val mappings: Map<UUID, RecordFieldsMapping>
        get() = project.getComponent(RecordFieldsMappingsStore::class.java).mappings

    private val recordsTable = ListTable()
    @Suppress("UNCHECKED_CAST")
    private val listTableModel: ListTableModel<RecordModel>
        get() = recordsTable.model as ListTableModel<RecordModel>

    init {
        log.trace("Init editor ${this.javaClass}")
        hideTable()

        val mappingItems = listOf(MappingItem(null, ""))
            .plus(mappings.map { MappingItem(it.key, it.value.name) })
            .toTypedArray()
        mappingComboBox.model = DefaultComboBoxModel(mappingItems)

        applyMappingButton.addActionListener {
            applyMappingAction(openedFile)
        }

        val recordsTableDecorator = ToolbarDecorator.createDecorator(recordsTable)
        recordsTablePanel.add(recordsTableDecorator.createPanel(), BorderLayout.CENTER)
    }

    private fun applyMappingAction(openedFile: VirtualFile) {
        val key = selectedMappingItem.key
        if (key != null) {
            mappingLinksStore.updateLink(openedFile, key)
            val mapping = mappings[key] ?: error("No mapping with key $key")
            setTableModel(mapping)
        } else {
            mappingLinksStore.removeLink(openedFile)
            setEmptyTableModel()
        }
    }

    private fun setTableModel(mapping: RecordFieldsMapping) {
        val records = readRecords(mapping)
        val columns = mapping.fieldDescriptors.mapIndexed() { i, d -> FieldColumn(i, d.name) }
        recordsTable.updateModel(ListTableModel(records, columns))
        showTable()
    }

    private fun readRecords(mapping: RecordFieldsMapping): MutableList<RecordModel> {
        return recordsReader.readRecords(mapping, document).map { RecordModel(it) }.toMutableList()
    }

    private fun setEmptyTableModel() {
        recordsTable.updateModel(ListTableModel.EMPTY_MODEL)
        hideTable()
    }

    private fun showTable() {
        recordsTablePanel.isVisible = true
        noMappingSelectedPanel.isVisible = false
    }

    private fun hideTable() {
        recordsTablePanel.isVisible = false
        noMappingSelectedPanel.isVisible = true
    }

    override fun selectNotify() {
        val key = mappingLinksStore.getLink(openedFile)?.mappingId
        if (key != null) {
            val mapping = mappings[key] ?: error("No mapping with key $key")
            mappingComboBox.selectedItem = MappingItem(key, mapping.name)
            setTableModel(mapping)
        }
    }

    override fun getComponent(): JComponent = mainPanel

    override fun getPreferredFocusedComponent(): JComponent? = mainPanel

    override fun isModified(): Boolean = false

    companion object {
        private val log = LoggerFactory.getLogger(RecordsEditorForm::class.java)
    }
}

private data class RecordModel(val fields: MutableList<FieldModel>) {
    constructor(domainModel: Record) : this(domainModel.fields.map { FieldModel(it) }.toMutableList())

    fun toDomainModel(): Record = Record(this.fields.map { it.toDomainModel() })
}

private data class FieldModel(var value: String, var fieldDescriptor: RecordFieldDescriptor) {
    constructor(domainModel: Field) : this(domainModel.value, domainModel.fieldDescriptor)

    fun toDomainModel(): Field = Field(this.value, this.fieldDescriptor)
}

private class FieldColumn(val fieldIndex: Int, val fieldName: String) : Column<RecordModel> {
    override fun getCellValue(row: RecordModel): Any = row.fields[fieldIndex].value

    override fun setCellValue(row: RecordModel, newValue: Any) {
        row.fields[fieldIndex].value = newValue as String
    }

    override fun isEditable(): Boolean = false

    override fun getColumnClass(): Class<*> = String::class.java

    override fun getName(): String = fieldName
}

data class MappingItem(val key: UUID?, val name: String) {
    override fun toString(): String = name
}
