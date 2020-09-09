package com.github.szymon2603.fixedlengthrecordseditor.components

import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldDescriptor
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldsConfig
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordValueAlignment
import com.intellij.util.xmlb.annotations.Attribute
import com.intellij.util.xmlb.annotations.Tag
import com.intellij.util.xmlb.annotations.XCollection

@Tag("RecordFieldsConfigs")
data class RecordFieldsConfigsElement(
    @XCollection(propertyElementName = "Configs")
    val configs: List<RecordFieldsConfigElement> = emptyList()
)

@Tag("RecordFieldsConfig")
data class RecordFieldsConfigElement(
    @Attribute
    val fileProjectPath: String = "",
    @XCollection(propertyElementName = "RecordFieldDescriptors")
    val recordFieldDescriptors: List<RecordFieldDescriptorElement> = emptyList()
) {
    constructor(modelValue: RecordFieldsConfig) : this(
        modelValue.fileProjectPath,
        modelValue.recordFieldDescriptors.map { RecordFieldDescriptorElement(it) }
    )

    fun convert() = RecordFieldsConfig(fileProjectPath, recordFieldDescriptors.map { it.convert() })
}

@Tag("RecordFieldDescriptor")
data class RecordFieldDescriptorElement(
    @Attribute val name: String = "",
    @Attribute val startIndex: Int = 0,
    @Attribute val endIndex: Int = 1,
    @Attribute val alignment: RecordValueAlignmentState = RecordValueAlignmentState.LEFT
) {
    constructor(modelValue: RecordFieldDescriptor) : this(
        modelValue.name,
        modelValue.startIndex,
        modelValue.endIndex,
        RecordValueAlignmentState.fromModelValue(modelValue.alignment)
    )

    fun convert() = RecordFieldDescriptor(
        name = name,
        startIndex = startIndex,
        endIndex = endIndex,
        alignment = alignment.modelValue
    )
}

enum class RecordValueAlignmentState(val modelValue: RecordValueAlignment) {
    LEFT(RecordValueAlignment.LEFT),
    CENTER(RecordValueAlignment.CENTER),
    RIGHT(RecordValueAlignment.RIGHT);

    companion object {
        fun fromModelValue(modelValue: RecordValueAlignment): RecordValueAlignmentState {
            return when (modelValue) {
                RecordValueAlignment.LEFT -> LEFT
                RecordValueAlignment.CENTER -> CENTER
                RecordValueAlignment.RIGHT -> RIGHT
            }
        }
    }
}
