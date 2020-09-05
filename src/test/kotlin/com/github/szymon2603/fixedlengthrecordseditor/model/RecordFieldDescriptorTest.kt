package com.github.szymon2603.fixedlengthrecordseditor.model

import org.junit.Test
import java.lang.IllegalArgumentException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class RecordFieldDescriptorTest {

    @Test
    fun `test setter and getter for name field works`() {
        val recordFieldConfig = RecordFieldDescriptor()

        val actual = recordFieldConfig.copy(0, "ID")
        assertEquals("ID", actual.name)
        assertEquals("ID", actual[0])
    }

    @Test
    fun `test setter and getter for startIndex field works`() {
        val recordFieldConfig = RecordFieldDescriptor()

        val actual = recordFieldConfig.copy(1, 0)
        assertEquals(0, actual.startIndex)
        assertEquals(0, actual[1])
    }

    @Test
    fun `test setter and getter for endIndex field works`() {
        val recordFieldConfig = RecordFieldDescriptor()

        val actual = recordFieldConfig.copy(2, 10)
        assertEquals(10, actual.endIndex)
        assertEquals(10, actual[2])
    }

    @Test
    fun `test setter and getter for length field works`() {
        val recordFieldConfig = RecordFieldDescriptor()

        val actual = recordFieldConfig.copy(3, 11)
        assertEquals(11, actual.length)
        assertEquals(11, actual[3])
    }

    @Test
    fun `test setter and getter for alignment field works`() {
        val recordFieldConfig = RecordFieldDescriptor()

        val actual = recordFieldConfig.copy(4, RecordValueAlignment.LEFT)
        assertEquals(RecordValueAlignment.LEFT, actual.alignment)
        assertEquals(RecordValueAlignment.LEFT, actual[4])
    }

    @Test
    fun `test throw exception on wrong index when set`() {
        val recordFieldConfig = RecordFieldDescriptor()

        val result = runCatching { recordFieldConfig.copy(-1, "Value") }
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `test throw exception on wrong index when get`() {
        val recordFieldConfig = RecordFieldDescriptor()

        val result = runCatching { recordFieldConfig[-1] }
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `test length is calculated when start and end index are provided in constructor`() {
        val recordFieldConfig = RecordFieldDescriptor(startIndex = 2, endIndex = 8)

        assertEquals(6, recordFieldConfig.length)
    }

    @Test
    fun `test length is calculated when start and end index are provided in copy method`() {
        val recordFieldConfig = RecordFieldDescriptor(startIndex = 1).copyWithNewEndIndex(endIndex = 8)

        assertEquals(7, recordFieldConfig.length)
    }

    @Test
    fun `test endIndex is calculated when start and end length are provided in copy method`() {
        val recordFieldConfig = RecordFieldDescriptor(startIndex = 1).copyWithNewLength(8)

        assertEquals(9, recordFieldConfig.endIndex)
    }
}
