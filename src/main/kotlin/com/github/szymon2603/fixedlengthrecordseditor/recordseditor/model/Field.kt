package com.github.szymon2603.fixedlengthrecordseditor.recordseditor.model

import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldDescriptor
import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordValueAlignment
import org.apache.commons.lang.StringUtils

data class Field(val value: String, val fieldDescriptor: RecordFieldDescriptor) {
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
