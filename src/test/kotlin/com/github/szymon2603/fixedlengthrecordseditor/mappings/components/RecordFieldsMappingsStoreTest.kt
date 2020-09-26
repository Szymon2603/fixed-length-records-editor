package com.github.szymon2603.fixedlengthrecordseditor.mappings.components

import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldDescriptor
import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldsMapping
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
