package com.github.szymon2603.fixedlengthrecordseditor.layout.model

import java.util.regex.Pattern

data class RecordsLayout(
    var name: String = "",
    val recordMappings: MutableList<RecordMapping> = mutableListOf()
)

data class RecordMapping(
    var name: String = "",
    val fieldMappings: MutableList<FieldMapping> = mutableListOf(),
    var recordSelector: RecordSelector = EveryRowRecordSelector
) {
    override fun toString(): String {
        return name
    }
}

data class FieldMapping(
    var name: String = "",
    var startIndex: Int = 0,
    var endIndex: Int = 1,
    var type: FieldType = FieldType.STRING,
    var alignment: FieldAlignment = FieldAlignment.LEFT
) {
    val length: Int
        get() = endIndex - startIndex

    fun copyWithNewLength(length: Int): FieldMapping {
        return this.copy(endIndex = startIndex + length)
    }

    enum class FieldType {
        STRING, NUMBER, DATE
    }

    enum class FieldAlignment {
        LEFT, CENTER, RIGHT
    }
}

interface RecordSelector {
    fun canApplyMapping(record: String, lineNumber: Long): Boolean
}

object EveryRowRecordSelector : RecordSelector {
    override fun canApplyMapping(record: String, lineNumber: Long) = true
}

data class LineNumberRecordSelector(var lineNumber: Long) : RecordSelector {
    override fun canApplyMapping(record: String, lineNumber: Long): Boolean {
        return this.lineNumber == lineNumber
    }
}

data class RegexRecordSelector(var regex: String) : RecordSelector {
    override fun canApplyMapping(record: String, lineNumber: Long): Boolean {
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(record)
        return matcher.matches()
    }
}