package com.github.szymon2603.fixedlengthrecordseditor.recordseditor

import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldDescriptor
import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordValueAlignment
import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.model.Field
import com.intellij.util.indexing.impl.DebugAssertions.assertTrue
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

internal class FieldTest {

    @Test
    fun `test that format method handle LEFT alignment option`() {
        val simpleFieldDescriptor = RecordFieldDescriptor(endIndex = 5, alignment = RecordValueAlignment.LEFT)
        val field = Field("val", simpleFieldDescriptor)

        assertEquals("val  ", field.getFormatted())
    }

    @Test
    fun `test that format method handle CENTER alignment option`() {
        val simpleFieldDescriptor = RecordFieldDescriptor(endIndex = 5, alignment = RecordValueAlignment.CENTER)
        val field = Field("val", simpleFieldDescriptor)

        assertEquals(" val ", field.getFormatted())
    }

    @Test
    fun `test that format method handle RIGHT alignment option`() {
        val simpleFieldDescriptor = RecordFieldDescriptor(endIndex = 5, alignment = RecordValueAlignment.RIGHT)
        val field = Field("val", simpleFieldDescriptor)

        assertEquals("  val", field.getFormatted())
    }

    @Test
    fun `test that when length is ok then isValid method returns true`() {
        val simpleFieldDescriptor = RecordFieldDescriptor(endIndex = 2)
        val field = Field("va", simpleFieldDescriptor)

        assertTrue(field.isValid())
    }

    @Test
    fun `test that when length is not ok then isValid method returns false`() {
        val simpleFieldDescriptor = RecordFieldDescriptor(endIndex = 2)
        val field = Field("val", simpleFieldDescriptor)

        assertFalse(field.isValid())
    }
}
