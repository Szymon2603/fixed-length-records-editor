package com.github.szymon2603.fixedlengthrecordseditor.model

import javax.swing.table.AbstractTableModel

class RecordFieldsMappingTableModel(private var mapping: RecordFieldsMapping) : AbstractTableModel() {

    val recordFieldsMapping: RecordFieldsMapping
        get() = mapping

    override fun getRowCount(): Int = mapping.numberOfFields

    override fun getColumnCount(): Int = RecordFieldDescriptor.NUMBER_OF_CONFIG_ATTRIBUTES

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any? {
        return getRecordFieldsConfig(rowIndex)[columnIndex]
    }

    override fun setValueAt(value: Any?, rowIndex: Int, columnIndex: Int) {
        val recordFieldDescriptors = mapping.fieldDescriptors
        val fieldDescriptor = recordFieldDescriptors[rowIndex]
        val newFieldDescriptor = fieldDescriptor.copy(columnIndex, value)
        mapping = mapping.copyWithRecordFieldDescriptor(rowIndex, newFieldDescriptor)
    }

    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean = true

    private fun getRecordFieldsConfig(rowIndex: Int): RecordFieldDescriptor {
        val recordFieldsConfig = mapping.fieldDescriptors
        return recordFieldsConfig[rowIndex]
    }

    fun addNewRow() {
        val lastDescriptor = mapping.fieldDescriptors.last()
        val newDescriptor = RecordFieldDescriptor(
            startIndex = lastDescriptor.endIndex + 1,
            endIndex = lastDescriptor.endIndex + 2
        )
        mapping = mapping.copyWithRecordFieldDescriptor(newDescriptor)
    }

    fun removeRow(index: Int) {
        val recordFieldDescriptors = mapping.fieldDescriptors
        val rowToBeRemoved = recordFieldDescriptors[index]
        mapping = mapping.copyWithoutRecordFieldDescriptor(rowToBeRemoved)
    }
}
