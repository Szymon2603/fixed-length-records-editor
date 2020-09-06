package com.github.szymon2603.fixedlengthrecordseditor.model

import javax.swing.table.AbstractTableModel

class RecordTableModel(
    private var records: List<Record>,
    private var recordFieldsConfig: RecordFieldsConfig
) : AbstractTableModel() {

    val recordsView: List<Record>
        get() = records.toList()

    override fun getRowCount(): Int = records.size

    override fun getColumnCount(): Int = recordFieldsConfig.numberOfFields

    override fun getColumnName(column: Int): String {
        return recordFieldsConfig.recordFieldDescriptors[column].name
    }

    override fun findColumn(columnName: String?): Int {
        return recordFieldsConfig.recordFieldDescriptors.indexOfFirst { it.name == columnName }
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        return records[rowIndex].fields[columnIndex].value
    }

    override fun getColumnClass(columnIndex: Int): Class<*> {
        return String::class.java
    }

    override fun setValueAt(value: Any, rowIndex: Int, columnIndex: Int) {
        records = records.toMutableList().apply {
            val record = this[rowIndex]
            val fields = record.fields.toMutableList().apply {
                val field = this[columnIndex]
                this[columnIndex] = field.copy(value = value as String)
            }.toList()
            this[rowIndex] = record.copy(fields = fields)
        }.toList()
    }

    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean = true

    fun addRecord() {
        val fields = recordFieldsConfig.recordFieldDescriptors.map {
            Field("", it)
        }
        records = records.plus(Record(fields))
    }

    fun removeRecordAt(rowIndex: Int) {
        val recordToBeRemoved = records[rowIndex]
        records = records.minus(recordToBeRemoved)
    }
}
