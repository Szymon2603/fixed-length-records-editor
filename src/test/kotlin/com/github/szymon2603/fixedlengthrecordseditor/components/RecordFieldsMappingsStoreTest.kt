package com.github.szymon2603.fixedlengthrecordseditor.components

import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldDescriptor
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldsMapping
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

internal class RecordFieldsMappingsStoreTest {

    @Test
    fun `test that default state is empty list`() {
        val component = RecordFieldsMappingsStore()
        val actual = component.mappings

        assertEquals(emptyMap<UUID, RecordFieldsMapping>(), actual)
    }

    @Test
    fun `test that add method adds new item`() {
        val component = RecordFieldsMappingsStore()
        val id = component.addConfig(RecordFieldsMapping())
        val actual = component.mappings

        assertEquals(mapOf(id to RecordFieldsMapping()), actual)
    }

    @Test
    fun `test that update method update it without changing order`() {
        val component = RecordFieldsMappingsStore()
        val id1 = component.addConfig(RecordFieldsMapping("file1.txt"))
        val id2 = component.addConfig(RecordFieldsMapping("file2.txt"))

        component.updateConfig(id2, RecordFieldsMapping("file2.txt", listOf(RecordFieldDescriptor())))
        val actual = component.mappings

        val expected = mapOf(
            id1 to RecordFieldsMapping("file1.txt"),
            id2 to RecordFieldsMapping("file2.txt", listOf(RecordFieldDescriptor()))
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test that update method add new row if it doesn't exists`() {
        val component = RecordFieldsMappingsStore()
        val id1 = component.addConfig(RecordFieldsMapping("file1.txt"))

        val id2 = component.updateConfig(
            UUID.randomUUID(),
            RecordFieldsMapping("file2.txt", listOf(RecordFieldDescriptor()))
        )
        val actual = component.mappings

        val expected = mapOf(
            id1 to RecordFieldsMapping("file1.txt"),
            id2 to RecordFieldsMapping("file2.txt", listOf(RecordFieldDescriptor()))
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test load state convert model`() {
        val component = RecordFieldsMappingsStore()

        val descriptorElement = RecordFieldDescriptorElement("column")
        val id = UUID.randomUUID()
        val recordFieldsConfigElement = RecordFieldsMappingElement(id, "file.txt", listOf(descriptorElement))
        val recordFieldsConfigsElement = RecordFieldsMappingsState(listOf(recordFieldsConfigElement))

        component.loadState(recordFieldsConfigsElement)
        val actual = component.mappings

        val expected = mapOf(
            id to RecordFieldsMapping("file.txt", listOf(RecordFieldDescriptor("column")))
        )
        assertEquals(expected, actual)
    }
}
