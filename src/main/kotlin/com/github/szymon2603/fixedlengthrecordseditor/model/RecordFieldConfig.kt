package com.github.szymon2603.fixedlengthrecordseditor.model

import java.lang.IllegalArgumentException

data class RecordFieldsConfig(
    val fileProjectPath: String,
    val recordFieldConfigs: List<RecordFieldConfig>,
    val numberOfFields: Int = recordFieldConfigs.size
)

data class RecordFieldConfig(
    var name: String? = null,
    var startIndex: Int? = null,
    var endIndex: Int? = null,
    var length: Int? = null,
    var alignment: RecordValueAlignment? = null
) {
    companion object {
        const val NUMBER_OF_CONFIG_ATTRIBUTES = 5
    }

    operator fun get(attributeIndex: Int): Any? {
        return when (attributeIndex) {
            0 -> name
            1 -> startIndex
            2 -> endIndex
            3 -> length
            4 -> alignment
            else -> throw IllegalArgumentException("Attribute index [$attributeIndex] is not correct")
        }
    }

    operator fun set(attributeIndex: Int, value: Any?) {
        when (attributeIndex) {
            0 -> name = value as String?
            1 -> startIndex = value as Int?
            2 -> endIndex = value as Int?
            3 -> length = value as Int?
            4 -> alignment = value as RecordValueAlignment
            else -> throw IllegalArgumentException("Attribute index [$attributeIndex] is not correct")
        }
    }
}

enum class RecordValueAlignment {
    LEFT,
    CENTER,
    RIGHT
}
