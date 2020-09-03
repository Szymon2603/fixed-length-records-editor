package com.github.szymon2603.fixedlengthrecordseditor.model

import org.junit.Test
import java.lang.IllegalArgumentException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class RecordFieldConfigTest {

    @Test
    fun `test setter and getter for name field works`() {
        val recordFieldConfig = RecordFieldConfig()

        recordFieldConfig[0] = "ID"
        assertEquals("ID", recordFieldConfig.name)
        assertEquals("ID", recordFieldConfig[0])
    }

    @Test
    fun `test setter and getter for startIndex field works`() {
        val recordFieldConfig = RecordFieldConfig()

        recordFieldConfig[1] = 0
        assertEquals(0, recordFieldConfig.startIndex)
        assertEquals(0, recordFieldConfig[1])
    }

    @Test
    fun `test setter and getter for endIndex field works`() {
        val recordFieldConfig = RecordFieldConfig()

        recordFieldConfig[2] = 10
        assertEquals(10, recordFieldConfig.endIndex)
        assertEquals(10, recordFieldConfig[2])
    }

    @Test
    fun `test setter and getter for length field works`() {
        val recordFieldConfig = RecordFieldConfig()

        recordFieldConfig[3] = 11
        assertEquals(11, recordFieldConfig.length)
        assertEquals(11, recordFieldConfig[3])
    }

    @Test
    fun `test setter and getter for alignment field works`() {
        val recordFieldConfig = RecordFieldConfig()

        recordFieldConfig[4] = RecordValueAlignment.LEFT
        assertEquals(RecordValueAlignment.LEFT, recordFieldConfig.alignment)
        assertEquals(RecordValueAlignment.LEFT, recordFieldConfig[4])
    }

    @Test
    fun `test throw exception on wrong index when set`() {
        val recordFieldConfig = RecordFieldConfig()

        val result = runCatching { recordFieldConfig[-1] = "Value" }
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `test throw exception on wrong index when get`() {
        val recordFieldConfig = RecordFieldConfig()

        val result = runCatching { recordFieldConfig[-1] }
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }
}
