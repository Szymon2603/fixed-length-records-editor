package com.github.szymon2603.fixedlengthrecordseditor.model

import org.apache.commons.lang.StringUtils

data class Record(
    val fields: List<Field>
)

data class Field(
    val value: String,
    val fieldDescriptor: RecordFieldDescriptor
) {
    fun getFormatted(): String {
        return when (fieldDescriptor.alignment) {
            RecordValueAlignment.LEFT -> value.padEnd(fieldDescriptor.length)
            RecordValueAlignment.CENTER -> StringUtils.center(value, fieldDescriptor.length)
            RecordValueAlignment.RIGHT -> value.padStart(fieldDescriptor.length)
        }
    }

    fun isValid(): Boolean {
        return value.length <= fieldDescriptor.length
    }
}
