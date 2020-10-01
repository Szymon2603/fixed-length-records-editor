package com.github.szymon2603.fixedlengthrecordseditor.mappings.ui

import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldDescriptor
import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldsMapping
import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordValueAlignment
import com.jetbrains.rd.util.first
import org.junit.Test
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RecordFieldsMappingsFormModelTest {

    @Test
    fun `test that form model is created properly for empty initial model`() {
        // Given
        val initialModel = emptyList<Pair<UUID, RecordFieldsMapping>>()

        // When
        val recordFieldsMappingsFormModel = RecordFieldsMappingsFormModel(initialModel)

        // Then
        assertEquals(emptyMap(), recordFieldsMappingsFormModel.currentFormState)
    }

    @Test
    fun `test that form model is created properly for not empty initial model with mappings`() {
        // Given
        val initialModel = notEmptyMappings()

        // When
        val recordFieldsMappingsFormModel = RecordFieldsMappingsFormModel(initialModel)

        // Then
        assertEquals(notEmptyMappings().toMap(), recordFieldsMappingsFormModel.currentFormState)
    }

    @Test
    fun `test that form model is created properly for not empty initial model with mappings and descriptors`() {
        // Given
        val initialModel = notEmptyMappingsAndDescriptors()

        // When
        val recordFieldsMappingsFormModel = RecordFieldsMappingsFormModel(initialModel)

        // Then
        assertEquals(notEmptyMappingsAndDescriptors().toMap(), recordFieldsMappingsFormModel.currentFormState)
    }

    @Test
    fun `test that add mapping adds it to state for empty initial state`() {
        // Given
        val initialModel = emptyList<Pair<UUID, RecordFieldsMapping>>()
        val uuidGenerator = fixedUUIDGenerator()
        val recordFieldsMappingsFormModel = RecordFieldsMappingsFormModel(initialModel, uuidGenerator)

        // When
        recordFieldsMappingsFormModel.addMapping(-1)

        // Then
        val expected = mapOf(uuidGenerator() to RecordFieldsMapping("New mapping"))
        assertEquals(expected, recordFieldsMappingsFormModel.currentFormState)
    }

    @Test
    fun `test that add mapping adds it after selected row`() {
        // Given
        val initialModel = twoMappings()
        val uuidGenerator = fixedUUIDGenerator()
        val recordFieldsMappingsFormModel = RecordFieldsMappingsFormModel(initialModel, uuidGenerator)

        // When
        recordFieldsMappingsFormModel.addMapping(0)

        // Then
        val expected = mapOf(
            UUID.fromString("7b735d05-11ba-49a6-ba1f-fb81f6e4c823") to RecordFieldsMapping("mapping"),
            UUID.fromString("7b735d05-11ba-49a6-ba1f-fb81f6e4c823") to RecordFieldsMapping("New mapping"),
            UUID.fromString("7b735d05-11ba-49a6-ba1f-fb81f6e4c824") to RecordFieldsMapping("mapping")
        )
        assertEquals(expected, recordFieldsMappingsFormModel.currentFormState)
    }

    @Test
    fun `test that remove mapping remove selected mapping`() {
        // Given
        val initialModel = twoMappings()
        val uuidGenerator = fixedUUIDGenerator()
        val recordFieldsMappingsFormModel = RecordFieldsMappingsFormModel(initialModel, uuidGenerator)

        // When
        recordFieldsMappingsFormModel.removeMapping(0)

        // Then
        val expected = mapOf(
            UUID.fromString("7b735d05-11ba-49a6-ba1f-fb81f6e4c824") to RecordFieldsMapping("mapping")
        )
        assertEquals(expected, recordFieldsMappingsFormModel.currentFormState)
    }

    @Test
    fun `test that add descriptor adds it to state for empty initial state`() {
        // Given
        val initialModel = notEmptyMappings()
        val uuidGenerator = fixedUUIDGenerator()
        val recordFieldsMappingsFormModel = RecordFieldsMappingsFormModel(initialModel, uuidGenerator)

        // When
        recordFieldsMappingsFormModel.addDescriptor(-1)

        // Then
        val expected = listOf(RecordFieldDescriptor("New column", 0, 1, RecordValueAlignment.LEFT))
        assertEquals(expected, recordFieldsMappingsFormModel.currentFormState.first().value.fieldDescriptors)
    }

    @Test
    fun `test that add descriptor adds it after selected row`() {
        // Given
        val initialModel = twoDescriptors()
        val uuidGenerator = fixedUUIDGenerator()
        val recordFieldsMappingsFormModel = RecordFieldsMappingsFormModel(initialModel, uuidGenerator)

        // When
        recordFieldsMappingsFormModel.addDescriptor(0)

        // Then
        val expected = listOf(
            RecordFieldDescriptor("column one", 0, 1, RecordValueAlignment.LEFT),
            RecordFieldDescriptor("New column", 0, 1, RecordValueAlignment.LEFT),
            RecordFieldDescriptor("column two", 1, 5, RecordValueAlignment.LEFT)
        )
        assertEquals(expected, recordFieldsMappingsFormModel.currentFormState.first().value.fieldDescriptors)
    }

    @Test
    fun `test that remove descriptor remove selected descriptor`() {
        // Given
        val initialModel = twoDescriptors()
        val uuidGenerator = fixedUUIDGenerator()
        val recordFieldsMappingsFormModel = RecordFieldsMappingsFormModel(initialModel, uuidGenerator)

        // When
        recordFieldsMappingsFormModel.removeDescriptor(0)

        // Then
        val expected = listOf(
            RecordFieldDescriptor("column two", 1, 5, RecordValueAlignment.LEFT)
        )
        assertEquals(expected, recordFieldsMappingsFormModel.currentFormState.first().value.fieldDescriptors)
    }

    @Test
    fun `test that isDescriptorListEmpty return true when index is -1`() {
        // Given
        val recordFieldsMappingsFormModel = RecordFieldsMappingsFormModel(emptyList())

        // When
        val actual = recordFieldsMappingsFormModel.isDescriptorListEmpty(-1)

        // Then
        assertTrue(actual)
    }

    @Test
    fun `test that isDescriptorListEmpty return true when descriptors list is empty`() {
        // Given
        val recordFieldsMappingsFormModel = RecordFieldsMappingsFormModel(notEmptyMappings())

        // When
        val actual = recordFieldsMappingsFormModel.isDescriptorListEmpty(0)

        // Then
        assertTrue(actual)
    }

    @Test
    fun `test that isDescriptorListEmpty return false when descriptors list is not empty`() {
        // Given
        val recordFieldsMappingsFormModel = RecordFieldsMappingsFormModel(notEmptyMappingsAndDescriptors())

        // When
        val actual = recordFieldsMappingsFormModel.isDescriptorListEmpty(0)

        // Then
        assertFalse(actual)
    }

    @Test
    fun `test that descriptors are set as empty list for mapping index eqauls -1`() {
        // Given
        val recordFieldsMappingsFormModel = RecordFieldsMappingsFormModel(twoMappingsWithDescriptors())

        // When
        recordFieldsMappingsFormModel.updateDescriptorsModelFor(-1)

        // Then
        assertEquals(0, recordFieldsMappingsFormModel.descriptorsTableModel.rowCount)
    }

    @Test
    fun `test that descriptors are updated for selected mappings`() {
        // Given
        val recordFieldsMappingsFormModel = RecordFieldsMappingsFormModel(twoMappingsWithDescriptors())

        // When
        recordFieldsMappingsFormModel.updateDescriptorsModelFor(1)

        // Then
        assertEquals(2, recordFieldsMappingsFormModel.descriptorsTableModel.rowCount)
    }

    private fun notEmptyMappings(): List<Pair<UUID, RecordFieldsMapping>> = listOf(
        UUID.fromString("7b735d05-11ba-49a6-ba1f-fb81f6e4c823") to RecordFieldsMapping("mapping")
    )

    private fun twoMappings(): List<Pair<UUID, RecordFieldsMapping>> = listOf(
        UUID.fromString("7b735d05-11ba-49a6-ba1f-fb81f6e4c823") to RecordFieldsMapping("mapping"),
        UUID.fromString("7b735d05-11ba-49a6-ba1f-fb81f6e4c824") to RecordFieldsMapping("mapping")
    )

    private fun notEmptyMappingsAndDescriptors(): List<Pair<UUID, RecordFieldsMapping>> {
        val descriptors = listOf(
            RecordFieldDescriptor("column one", 0, 5, RecordValueAlignment.CENTER)
        )
        val uuid = UUID.fromString("7b735d05-11ba-49a6-ba1f-fb81f6e4c825")
        return listOf(
            uuid to RecordFieldsMapping("mapping", descriptors)
        )
    }

    private fun twoDescriptors(): List<Pair<UUID, RecordFieldsMapping>> {
        val descriptors = listOf(
            RecordFieldDescriptor("column one", 0, 1, RecordValueAlignment.LEFT),
            RecordFieldDescriptor("column two", 1, 5, RecordValueAlignment.LEFT)
        )
        val uuid = UUID.fromString("7b735d05-11ba-49a6-ba1f-fb81f6e4c823")
        return listOf(
            uuid to RecordFieldsMapping("mapping", descriptors)
        )
    }

    private fun twoMappingsWithDescriptors(): List<Pair<UUID, RecordFieldsMapping>> {
        val firstMappingDescriptors = listOf(RecordFieldDescriptor())
        val secondMappingDescriptors = listOf(RecordFieldDescriptor(), RecordFieldDescriptor())
        val firstUUID = UUID.fromString("7b735d05-11ba-49a6-ba1f-fb81f6e4c823")
        val secondUUID = UUID.fromString("7b735d05-11ba-49a6-ba1f-fb81f6e4c824")
        return listOf(
            firstUUID to RecordFieldsMapping("mapping", firstMappingDescriptors),
            secondUUID to RecordFieldsMapping("mapping", secondMappingDescriptors)
        )
    }

    private fun fixedUUIDGenerator(): () -> UUID = { UUID.fromString("7b735d05-11ba-49a6-ba1f-fb81f6e4c823") }
}
