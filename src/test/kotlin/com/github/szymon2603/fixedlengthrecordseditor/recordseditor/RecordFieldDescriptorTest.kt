package com.github.szymon2603.fixedlengthrecordseditor.recordseditor

import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldDescriptor
import org.junit.Test
import kotlin.test.assertEquals

internal class RecordFieldDescriptorTest {

    @Test
    fun `test length is calculated when start and end index are provided in constructor`() {
        val recordFieldConfig = RecordFieldDescriptor(startIndex = 2, endIndex = 8)

        assertEquals(6, recordFieldConfig.length)
    }

    @Test
    fun `test length is calculated when start and end index are provided in copy method`() {
        val recordFieldConfig = RecordFieldDescriptor(startIndex = 1).copy(endIndex = 8)

        assertEquals(7, recordFieldConfig.length)
    }

    @Test
    fun `test endIndex is calculated when start and end length are provided in copy method`() {
        val recordFieldConfig = RecordFieldDescriptor(startIndex = 1).copyWithNewLength(8)

        assertEquals(9, recordFieldConfig.endIndex)
    }
}
