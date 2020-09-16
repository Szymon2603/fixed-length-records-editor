package com.github.szymon2603.fixedlengthrecordseditor.services

import com.github.szymon2603.fixedlengthrecordseditor.model.Field
import com.github.szymon2603.fixedlengthrecordseditor.model.Record
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldDescriptor
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldsMapping
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordValueAlignment
import com.intellij.openapi.components.ServiceManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase
import org.junit.Assert.*

class RecordsWriterTest : BasePlatformTestCase() {

    lateinit var service: RecordsWriter
    lateinit var columnOne: RecordFieldDescriptor
    lateinit var columnTwo: RecordFieldDescriptor
    lateinit var columnThree: RecordFieldDescriptor
    lateinit var mapping: RecordFieldsMapping
    lateinit var records: List<Record>

    override fun getTestDataPath(): String {
        return "./src/test/resources/services"
    }

    override fun setUp() {
        super.setUp()
        createTestMapping()
        createRecords()
        service = ServiceManager.getService(RecordsWriter::class.java)
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

    private fun createRecords() {
        val fieldOne = Field(value = "1234", fieldDescriptor = columnOne)
        val fieldTwo = Field(value = "A", fieldDescriptor = columnTwo)
        val fieldThree = Field(value = "00", fieldDescriptor = columnThree)
        val recordOne = Record(listOf(fieldOne, fieldTwo, fieldThree))
        val recordTwo = Record(listOf(fieldOne.copy(value = "4321"), fieldTwo.copy(value = "B"), fieldThree.copy("0")))
        records = listOf(recordOne, recordTwo)
    }

    fun `test that on empty file it writes records to file`() {
        // Given
        val file = myFixture.configureByFile("empty-file.txt")

        // When
        service.writeRecords(file, records)

        // Then
        val actual = file.virtualFile.inputStream.bufferedReader().readText()
        val separator = file.virtualFile.detectedLineSeparator
        val expected =
                "1234 A  00$separator" +
                "4321 B   0$separator"
        TestCase.assertEquals(expected, actual)
    }

    fun `test that on not empty file and minus one record it writes records to file`() {
        // Given
        val file = myFixture.configureByFile("records-writer-correct-file.txt")
        val records = this.records.subList(0, 1)

        // When
        service.writeRecords(file, records)

        // Then
        val actual = file.virtualFile.inputStream.bufferedReader().readText()
        val separator = file.virtualFile.detectedLineSeparator
        val expected = "1234 A  00$separator"
        TestCase.assertEquals(expected, actual)
    }

    fun `test that on not empty file and plus one record it writes records to file`() {
        // Given
        val file = myFixture.configureByFile("records-writer-correct-file.txt")
        val records = this.records.plus(createRecord())

        // When
        service.writeRecords(file, records)

        // Then
        val actual = file.virtualFile.inputStream.bufferedReader().readText()
        val separator = file.virtualFile.detectedLineSeparator
        val expected =
                "1234 A  00$separator" +
                "4321 B   0$separator" +
                "999  D  11$separator"
        TestCase.assertEquals(expected, actual)
    }

    private fun createRecord(): Record {
        val fieldOne = Field(value = "999", fieldDescriptor = columnOne)
        val fieldTwo = Field(value = "D", fieldDescriptor = columnTwo)
        val fieldThree = Field(value = "11", fieldDescriptor = columnThree)
        return Record(listOf(fieldOne, fieldTwo, fieldThree))
    }

    fun `test that on not empty file and replace one record it writes records to file`() {
        // Given
        val file = myFixture.configureByFile("records-writer-correct-file.txt")
        val records = this.records.subList(1, 2).plus(createRecord())

        // When
        service.writeRecords(file, records)

        // Then
        val actual = file.virtualFile.inputStream.bufferedReader().readText()
        val separator = file.virtualFile.detectedLineSeparator
        val expected =
                "4321 B   0$separator" +
                "999  D  11$separator"
        TestCase.assertEquals(expected, actual)
    }
}