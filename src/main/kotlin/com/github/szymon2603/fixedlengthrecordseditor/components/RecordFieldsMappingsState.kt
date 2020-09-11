package com.github.szymon2603.fixedlengthrecordseditor.components

import com.github.szymon2603.fixedlengthrecordseditor.components.converters.UUIDConverter
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldDescriptor
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldsMapping
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordValueAlignment
import com.intellij.util.xmlb.annotations.Attribute
import com.intellij.util.xmlb.annotations.Tag
import com.intellij.util.xmlb.annotations.XCollection
import java.util.UUID

data class RecordFieldsMappingsState(
    @XCollection(propertyElementName = "RecordFieldsMappings")
    val mappings: List<RecordFieldsMappingElement> = emptyList()
)

@Tag("RecordFieldsMapping")
data class RecordFieldsMappingElement(
    @Attribute(converter = UUIDConverter::class)
    val id: UUID = UUID.randomUUID(),
    @Attribute
    val name: String = "",
    @XCollection(propertyElementName = "RecordFieldDescriptors")
    val recordFieldDescriptors: List<RecordFieldDescriptorElement> = emptyList()
) {
    constructor(id: UUID, modelValue: RecordFieldsMapping) : this(
        id,
        modelValue.name,
        modelValue.fieldDescriptors.map { RecordFieldDescriptorElement(it) }
    )

    fun convert() = RecordFieldsMapping(name, recordFieldDescriptors.map { it.convert() })
}

@Tag("RecordFieldDescriptor")
data class RecordFieldDescriptorElement(
    @Attribute val name: String = "",
    @Attribute val startIndex: Int = 0,
    @Attribute val endIndex: Int = 1,
    @Attribute val alignment: RecordValueAlignmentElement = RecordValueAlignmentElement.LEFT
) {
    constructor(modelValue: RecordFieldDescriptor) : this(
        modelValue.name,
        modelValue.startIndex,
        modelValue.endIndex,
        RecordValueAlignmentElement.fromModelValue(modelValue.alignment)
    )

    fun convert() = RecordFieldDescriptor(
        name = name,
        startIndex = startIndex,
        endIndex = endIndex,
        alignment = alignment.modelValue
    )
}

enum class RecordValueAlignmentElement(val modelValue: RecordValueAlignment) {
    LEFT(RecordValueAlignment.LEFT),
    CENTER(RecordValueAlignment.CENTER),
    RIGHT(RecordValueAlignment.RIGHT);

    companion object {
        fun fromModelValue(modelValue: RecordValueAlignment): RecordValueAlignmentElement {
            return when (modelValue) {
                RecordValueAlignment.LEFT -> LEFT
                RecordValueAlignment.CENTER -> CENTER
                RecordValueAlignment.RIGHT -> RIGHT
            }
        }
    }
}
