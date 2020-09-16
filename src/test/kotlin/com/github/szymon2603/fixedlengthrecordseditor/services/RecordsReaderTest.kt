package com.github.szymon2603.fixedlengthrecordseditor.services

import com.github.szymon2603.fixedlengthrecordseditor.model.Field
import com.github.szymon2603.fixedlengthrecordseditor.model.Record
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldDescriptor
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldsMapping
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordValueAlignment
import com.intellij.openapi.components.ServiceManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase

class RecordsReaderTest : BasePlatformTestCase() {

    lateinit var service: RecordsReader
    lateinit var columnOne: RecordFieldDescriptor
    lateinit var columnTwo: RecordFieldDescriptor
    lateinit var columnThree: RecordFieldDescriptor
    lateinit var mapping: RecordFieldsMapping

    override fun getTestDataPath(): String {
        return "./src/test/resources/services"
    }

    override fun setUp() {
        super.setUp()
        createTestMapping()
        service = ServiceManager.getService(RecordsReader::class.java)
    }

    private fun createTestMapping() {
        columnOne = RecordFieldDescriptor(
            name = "Column A",
            startIndex = 0,
            endIndex = 4,
            alignment = RecordValueAlignment.LEFT
        )
        columnTwo = RecordFieldDescriptor(
            name = "Column B",
            startIndex = 4,
            endIndex = 7,
            alignment = RecordValueAlignment.CENTER
        )
        columnThree = RecordFieldDescriptor(
            name = "Column C",
            startIndex = 7,
            endIndex = 10,
            alignment = RecordValueAlignment.RIGHT
        )
        mapping = RecordFieldsMapping("test-mapping", listOf(columnOne, columnTwo, columnThree))
    }

    fun `test that on empty file it returns empty list`() {
        // Given
        val file = myFixture.configureByFile("empty-file.txt")

        // When
        val actual = service.readRecords(mapping, file)

        // Then
        TestCase.assertEquals(emptyList<Record>(), actual)
    }

    fun `test that on not empty and correct file it returns list of records`() {
        // Given
        val file = myFixture.configureByFile("records-reader-correct-file.txt")

        // When
        val actual = service.readRecords(mapping, file)

        // Then
        val fieldOne = Field(value = "1234", fieldDescriptor = columnOne)
        val fieldTwo = Field(value = "A", fieldDescriptor = columnTwo)
        val fieldThree = Field(value = "00", fieldDescriptor = columnThree)
        val recordOne = Record(listOf(fieldOne, fieldTwo, fieldThree))
        val recordTwo = Record(listOf(fieldOne.copy(value = "4321"), fieldTwo.copy(value = "B"), fieldThree.copy("0")))
        val expected = listOf(recordOne, recordTwo)
        TestCase.assertEquals(expected, actual)
    }
}
