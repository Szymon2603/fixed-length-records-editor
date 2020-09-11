package com.github.szymon2603.fixedlengthrecordseditor.model

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class RecordsTableModelTest {

    @Test
    fun `test that row count is number of records`() {
        val record = Record(listOf())
        val recordTableModel = RecordsTableModel(listOf(record, record), RecordFieldsMapping("", emptyList()))

        assertEquals(2, recordTableModel.rowCount)
    }

    @Test
    fun `test that column count is number of fields in records`() {
        val recordFieldDescriptors = listOf(RecordFieldDescriptor(), RecordFieldDescriptor(), RecordFieldDescriptor())
        val recordFieldsConfig = RecordFieldsMapping("", recordFieldDescriptors)
        val recordTableModel = RecordsTableModel(emptyList(), recordFieldsConfig)

        assertEquals(3, recordTableModel.columnCount)
    }

    @Test
    fun `test that column name is get from descriptor`() {
        val recordFieldDescriptors = listOf(RecordFieldDescriptor("field-name"))
        val recordFieldsConfig = RecordFieldsMapping("", recordFieldDescriptors)
        val recordTableModel = RecordsTableModel(emptyList(), recordFieldsConfig)

        assertEquals("field-name", recordTableModel.getColumnName(0))
    }

    @Test
    fun `test that column index is found correctly`() {
        val recordFieldDescriptors = listOf(RecordFieldDescriptor("A"), RecordFieldDescriptor("B"))
        val recordFieldsConfig = RecordFieldsMapping("", recordFieldDescriptors)
        val recordTableModel = RecordsTableModel(emptyList(), recordFieldsConfig)

        assertEquals(1, recordTableModel.findColumn("B"))
    }

    @Test
    fun `test that value of cell is retrieved`() {
        val fieldDescriptor = RecordFieldDescriptor("first-column")
        val field = Field("123", fieldDescriptor)
        val record = Record(listOf(field))
        val recordFieldsConfig = RecordFieldsMapping("", listOf(fieldDescriptor))
        val recordTableModel = RecordsTableModel(listOf(record, record), recordFieldsConfig)

        assertEquals("123", recordTableModel.getValueAt(0, 0))
    }

    @Test
    fun `test that each column type is String class`() {
        val recordFieldsConfig = RecordFieldsMapping("", emptyList())
        val recordTableModel = RecordsTableModel(emptyList(), recordFieldsConfig)

        assertEquals(String::class.java, recordTableModel.getColumnClass(0))
    }

    @Test
    fun `test that each cell is editable`() {
        val recordFieldsConfig = RecordFieldsMapping("", emptyList())
        val recordTableModel = RecordsTableModel(emptyList(), recordFieldsConfig)

        assertTrue(recordTableModel.isCellEditable(0, 0))
    }

    @Test
    fun `test that set value at update records list`() {
        val fieldDescriptor = RecordFieldDescriptor("first-column")
        val field = Field("123", fieldDescriptor)
        val recordOne = Record(listOf(field))
        val recordTwo = recordOne.copy(listOf(field.copy(value = "456")))
        val recordFieldsConfig = RecordFieldsMapping("", listOf(fieldDescriptor))
        val recordTableModel = RecordsTableModel(listOf(recordOne, recordTwo), recordFieldsConfig)

        recordTableModel.setValueAt("987", 1, 0)
        val newExpectedRecord = recordTwo.copy(listOf(field.copy(value = "987")))
        val expected = listOf(recordOne, newExpectedRecord)
        assertEquals(expected, recordTableModel.recordsView)
    }

    @Test
    fun `test that add record method adds it to records list`() {
        val firstColumn = RecordFieldDescriptor("first-column")
        val secondColumn = RecordFieldDescriptor("second-column")
        val recordFieldsConfig = RecordFieldsMapping("", listOf(firstColumn, secondColumn))
        val recordTableModel = RecordsTableModel(emptyList(), recordFieldsConfig)

        recordTableModel.addRecord()
        val expected = Record(listOf(Field("", firstColumn), Field("", secondColumn)))
        assertEquals(expected, recordTableModel.recordsView[0])
    }

    @Test
    fun `test that remove record remove its`() {
        val firstColumn = RecordFieldDescriptor("first-column")
        val recordOne = Record(listOf(Field("123", firstColumn)))
        val recordTwo = Record(listOf(Field("456", firstColumn)))
        val recordFieldsConfig = RecordFieldsMapping("", listOf(firstColumn))
        val recordTableModel = RecordsTableModel(listOf(recordOne, recordTwo), recordFieldsConfig)

        recordTableModel.removeRecordAt(1)
        val expected = listOf(Record(listOf(Field("123", firstColumn))))
        assertEquals(expected, recordTableModel.recordsView)
    }
}
