package com.github.szymon2603.fixedlengthrecordseditor.mappings.ui

import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldsMapping
import org.junit.Test
import java.util.UUID
import kotlin.test.assertEquals

class RecordFieldsMappingsFormModelTest {

    @Test
    fun `test that form model is created properly for empty initial model`() {
        // Given
        val initialModel = emptyList<Pair<UUID, RecordFieldsMapping>>()

        // When
        val recordFieldsMappingsFormModel = RecordFieldsMappingsFormModel(initialModel)

        // Then
        assertEquals(-1, recordFieldsMappingsFormModel.selectedMapping)
        assertEquals(-1, recordFieldsMappingsFormModel.selectedDescriptor)
    }
}
