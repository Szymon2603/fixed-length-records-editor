package com.github.szymon2603.fixedlengthrecordseditor.ui

import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldDescriptor
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldsMapping
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldsMappingTableModel
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class RecordFieldsMappingTableModelTest {

    @Test
    fun `test row count is returned correctly`() {
        val recordFieldDescriptors = listOf(RecordFieldDescriptor("some-field"))
        val recordFieldsConfig = RecordFieldsMapping("some-file.txt", recordFieldDescriptors)
        val tableModel = RecordFieldsMappingTableModel(recordFieldsConfig)

        assertEquals(1, tableModel.rowCount)
    }

    @Test
    fun `test column count is returned correctly`() {
        val recordFieldDescriptors = listOf(RecordFieldDescriptor("some-field"))
        val recordFieldsConfig = RecordFieldsMapping("some-file.txt", recordFieldDescriptors)
        val tableModel = RecordFieldsMappingTableModel(recordFieldsConfig)

        assertEquals(5, tableModel.columnCount)
    }

    @Test
    fun `test that value at row and column index is returned correctly`() {
        val recordFieldDescriptors = listOf(RecordFieldDescriptor("some-field"))
        val recordFieldsConfig = RecordFieldsMapping("some-file.txt", recordFieldDescriptors)
        val tableModel = RecordFieldsMappingTableModel(recordFieldsConfig)

        assertEquals("some-field", tableModel.getValueAt(0, 0))
    }

    @Test
    fun `test that value at row and column index is set correctly`() {
        val recordFieldDescriptors = listOf(
            RecordFieldDescriptor("some-field-one"),
            RecordFieldDescriptor("some-field-two")
        )
        val recordFieldsConfig = RecordFieldsMapping("some-file.txt", recordFieldDescriptors)
        val tableModel = RecordFieldsMappingTableModel(recordFieldsConfig)

        tableModel.setValueAt("different-value-for-field", 1, 0)

        assertEquals("different-value-for-field", tableModel.getValueAt(1, 0))
    }

    @Test
    fun `test that all cells are editable for row`() {
        val recordFieldDescriptors = listOf(RecordFieldDescriptor("some-field"))
        val recordFieldsConfig = RecordFieldsMapping("some-file.txt", recordFieldDescriptors)
        val tableModel = RecordFieldsMappingTableModel(recordFieldsConfig)

        tableModel.setValueAt("different-value-for-field", 0, 0)

        (0..5).forEach { assertTrue(tableModel.isCellEditable(0, it)) }
    }

    @Test
    fun `test that add new row at end of list`() {
        val recordFieldDescriptors = listOf(RecordFieldDescriptor("some-field", 0, 10))
        val recordFieldsConfig = RecordFieldsMapping("some-file.txt", recordFieldDescriptors)
        val tableModel = RecordFieldsMappingTableModel(recordFieldsConfig)

        tableModel.addNewRow()
        val actual = tableModel.recordFieldsMapping.fieldDescriptors[1]
        assertEquals(actual, RecordFieldDescriptor(startIndex = 11, endIndex = 12))
    }

    @Test
    fun `test that remove row remove it`() {
        val recordFieldDescriptors = listOf(
            RecordFieldDescriptor("some-field", 0, 10),
            RecordFieldDescriptor("some-field", 11, 15)
        )
        val recordFieldsConfig = RecordFieldsMapping("some-file.txt", recordFieldDescriptors)
        val tableModel = RecordFieldsMappingTableModel(recordFieldsConfig)

        tableModel.removeRow(0)
        assertEquals(1, tableModel.recordFieldsMapping.fieldDescriptors.size)
    }
}
