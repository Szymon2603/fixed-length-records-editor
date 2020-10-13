package com.github.szymon2603.fixedlengthrecordseditor.layout.model

import java.util.regex.Pattern

data class RecordsLayout(
    val recordMappings: MutableList<RecordMapping> = mutableListOf()
)

data class RecordMapping(
    val fieldMappings: MutableList<FieldMapping> = mutableListOf(),
    val recordDetector: RecordDetector = EveryRowRecordDetector
)

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

interface RecordDetector {
    fun canApplyMapping(record: String, lineNumber: Long): Boolean
}

object EveryRowRecordDetector : RecordDetector {
    override fun canApplyMapping(record: String, lineNumber: Long) = true
}

data class LineNumberRecordDetector(val lineNumber: Long) : RecordDetector {
    override fun canApplyMapping(record: String, lineNumber: Long): Boolean {
        return this.lineNumber == lineNumber
    }
}

data class RegexRecordDetector(var regex: String) : RecordDetector {
    override fun canApplyMapping(record: String, lineNumber: Long): Boolean {
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(record)
        return matcher.matches()
    }
}