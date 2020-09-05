package com.github.szymon2603.fixedlengthrecordseditor.model

import org.junit.Test
import kotlin.test.assertEquals

internal class RecordFieldsConfigTest {

    @Test
    fun `test that it adds new record with default values for empty list scenario`() {
        val config = RecordFieldsConfig("some-file.txt", emptyList())
                .copyWithRecordFieldDescriptor(RecordFieldDescriptor())
        val actual = config.recordFieldDescriptors[0]
        val expected = RecordFieldDescriptor()
        assertEquals(expected, actual)
    }

    @Test
    fun `test that it adds new record with default values for not empty list scenario`() {
        val config = RecordFieldsConfig("some-file.txt", listOf(RecordFieldDescriptor(name = "some-field")))
                .copyWithRecordFieldDescriptor(RecordFieldDescriptor(name = "some-different-field"))
        val actual = config.recordFieldDescriptors[1]
        val expected = RecordFieldDescriptor(name = "some-different-field")
        assertEquals(expected, actual)
    }

    @Test
    fun `test that it replace record with at provided index`() {
        val recordFieldDescriptors = listOf(
                RecordFieldDescriptor(name = "some-field"),
                RecordFieldDescriptor(name = "some-different-field")
        )
        val config = RecordFieldsConfig("some-file.txt", recordFieldDescriptors)
                .copyWithRecordFieldDescriptor(1, recordFieldDescriptors[1].copyWithNewEndIndex(10))
        val actual = config.recordFieldDescriptors[1]
        val expected = RecordFieldDescriptor(name = "some-different-field", endIndex = 10)
        assertEquals(expected, actual)
    }
}