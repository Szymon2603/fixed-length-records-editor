package com.github.szymon2603.fixedlengthrecordseditor.recordseditor.ui

import javax.swing.table.AbstractTableModel
import javax.swing.table.TableCellEditor

class ListTableModel<R>(private var data: MutableList<R>, private val columns: List<Column<R>>) : AbstractTableModel() {

    override fun getRowCount(): Int = data.size

    override fun getColumnCount(): Int = columns.size

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        val row = data[rowIndex]
        return columns[columnIndex].getCellValue(row)
    }

    override fun setValueAt(newValue: Any, rowIndex: Int, columnIndex: Int) {
        val row = data[rowIndex]
        columns[columnIndex].setCellValue(row, newValue)
        fireTableCellUpdated(rowIndex, columnIndex)
    }

    override fun getColumnClass(columnIndex: Int): Class<*> = columns[columnIndex].getColumnClass()

    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean = columns[columnIndex].isEditable()

    override fun getColumnName(column: Int): String = columns[column].getName()

    fun getCustomCellEditors() = columns
        .asSequence()
        .filter { it.getCellEditor() != null }
        .map { it.getColumnClass() to it.getCellEditor()!! }
        .toList()

    fun addRow(row: R, index: Int = data.size) {
        data.add(index, row)
        fireTableRowsInserted(index, index)
    }

    fun removeRow(index: Int) {
        data.removeAt(index)
        fireTableRowsDeleted(index, index)
    }

    fun update(data: MutableList<R>) {
        this.data = data
        fireTableDataChanged()
    }

    companion object {
        val EMPTY_MODEL = ListTableModel(emptyList<Any>().toMutableList(), emptyList())
    }
}

interface Column<R> {
    fun getCellValue(row: R): Any
    fun setCellValue(row: R, newValue: Any)
    fun isEditable(): Boolean
    fun getColumnClass(): Class<*>
    fun getName(): String
    fun getCellEditor(): TableCellEditor? { return null }
}
