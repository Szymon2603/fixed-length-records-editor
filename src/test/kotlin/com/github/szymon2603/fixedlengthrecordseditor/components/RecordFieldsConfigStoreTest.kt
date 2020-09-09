package com.github.szymon2603.fixedlengthrecordseditor.components

import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldDescriptor
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldsConfig
import org.junit.Assert.assertEquals
import org.junit.Test

internal class RecordFieldsConfigStoreTest {

    @Test
    fun `test that default state is empty list`() {
        val component = RecordFieldsConfigStore()
        val actual = component.configs

        assertEquals(emptyList<RecordFieldsConfig>(), actual)
    }

    @Test
    fun `test that add method adds new item`() {
        val component = RecordFieldsConfigStore()
        component.addConfig(RecordFieldsConfig())
        val actual = component.configs

        assertEquals(listOf(RecordFieldsConfig()), actual)
    }

    @Test
    fun `test that update method update it without changing order`() {
        val component = RecordFieldsConfigStore()
        component.addConfig(RecordFieldsConfig("file1.txt"))
        component.addConfig(RecordFieldsConfig("file2.txt"))

        component.updateConfig(RecordFieldsConfig("file2.txt", listOf(RecordFieldDescriptor())))
        val actual = component.configs

        val expected = listOf(
            RecordFieldsConfig("file1.txt"),
            RecordFieldsConfig("file2.txt", listOf(RecordFieldDescriptor()))
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test that update method add new row if it doesn't exists`() {
        val component = RecordFieldsConfigStore()
        component.addConfig(RecordFieldsConfig("file1.txt"))

        component.updateConfig(RecordFieldsConfig("file2.txt", listOf(RecordFieldDescriptor())))
        val actual = component.configs

        val expected = listOf(
            RecordFieldsConfig("file1.txt"),
            RecordFieldsConfig("file2.txt", listOf(RecordFieldDescriptor()))
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test load state convert model`() {
        val component = RecordFieldsConfigStore()

        val descriptorElement = RecordFieldDescriptorElement("column")
        val recordFieldsConfigElement = RecordFieldsConfigElement("file.txt", listOf(descriptorElement))
        val recordFieldsConfigsElement = RecordFieldsConfigsElement(listOf(recordFieldsConfigElement))

        component.loadState(recordFieldsConfigsElement)
        val actual = component.configs

        val expected = listOf(RecordFieldsConfig("file.txt", listOf(RecordFieldDescriptor("column"))))
        assertEquals(expected, actual)
    }
}
