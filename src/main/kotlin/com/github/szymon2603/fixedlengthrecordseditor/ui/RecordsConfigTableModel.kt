package com.github.szymon2603.fixedlengthrecordseditor.ui

import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldDescriptor
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldsConfig
import javax.swing.table.AbstractTableModel

class RecordsConfigTableModel(private var recordFieldsConfig: RecordFieldsConfig) : AbstractTableModel() {

    val recordFieldsConfigView: RecordFieldsConfig
        get() = recordFieldsConfig

    override fun getRowCount(): Int = recordFieldsConfig.numberOfFields

    override fun getColumnCount(): Int = RecordFieldDescriptor.NUMBER_OF_CONFIG_ATTRIBUTES

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any? {
        return getRecordFieldsConfig(rowIndex)[columnIndex]
    }

    override fun setValueAt(value: Any?, rowIndex: Int, columnIndex: Int) {
        val recordFieldDescriptors = recordFieldsConfig.recordFieldDescriptors
        val fieldDescriptor = recordFieldDescriptors[rowIndex]
        val newFieldDescriptor = fieldDescriptor.copy(columnIndex, value)
        recordFieldsConfig = recordFieldsConfig.copyWithRecordFieldDescriptor(rowIndex, newFieldDescriptor)
    }

    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean = true

    private fun getRecordFieldsConfig(rowIndex: Int): RecordFieldDescriptor {
        val recordFieldsConfig = recordFieldsConfig.recordFieldDescriptors
        return recordFieldsConfig[rowIndex]
    }

    fun addNewRow() {
        val lastDescriptor = recordFieldsConfig.recordFieldDescriptors.last()
        val newDescriptor = RecordFieldDescriptor(
                startIndex = lastDescriptor.endIndex + 1,
                endIndex = lastDescriptor.endIndex + 2
        )
        recordFieldsConfig = recordFieldsConfig.copyWithRecordFieldDescriptor(newDescriptor)
    }

    fun removeRow(index: Int) {
        val recordFieldDescriptors = recordFieldsConfig.recordFieldDescriptors
        val rowToBeRemoved = recordFieldDescriptors[index]
        recordFieldsConfig = recordFieldsConfig.copyWithoutRecordFieldDescriptor(rowToBeRemoved)
    }
}
