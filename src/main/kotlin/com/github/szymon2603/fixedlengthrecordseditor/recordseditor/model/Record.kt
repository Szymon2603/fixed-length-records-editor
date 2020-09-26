package com.github.szymon2603.fixedlengthrecordseditor.recordseditor.model

data class Record(val fields: List<Field>) {
    fun convertToString(): String = fields.joinToString(separator = "") { it.getFormatted() }
}
