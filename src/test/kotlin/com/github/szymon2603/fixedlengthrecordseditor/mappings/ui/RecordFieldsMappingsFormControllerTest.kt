package com.github.szymon2603.fixedlengthrecordseditor.mappings.ui

import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldDescriptor
import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldsMapping
import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordValueAlignment
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase
import java.util.UUID

class RecordFieldsMappingsFormControllerTest : BasePlatformTestCase() {

    fun `test that controller state will be created for empty initial config`() {
        val mappings = emptyMap<UUID, RecordFieldsMapping>()
        val controller = RecordFieldsMappingsFormController(mappings)
        TestCase.assertTrue(controller.state.isEmpty())
    }

    fun `test that controller state will be created for not empty mappings list but empty for descriptors list`() {
        val mappings = notEmptyMappingAndDescriptorList()
        val controller = RecordFieldsMappingsFormController(mappings)
        TestCase.assertEquals(mappings, controller.state)
    }

    fun `test that controller state will be created for not empty mappings and descriptors list`() {
        val mappings = notEmptyMapping()
        RecordFieldsMappingsFormController(mappings)
        val controller = RecordFieldsMappingsFormController(mappings)
        TestCase.assertEquals(mappings, controller.state)
    }

    private fun notEmptyMapping() = mapOf(
        UUID.randomUUID() to RecordFieldsMapping("mapping one", listOf())
    )

    private fun notEmptyMappingAndDescriptorList() = mapOf(
        UUID.randomUUID() to RecordFieldsMapping(
            "mapping one",
            listOf(RecordFieldDescriptor("column one", 0, 1, RecordValueAlignment.LEFT))
        )
    )
}
