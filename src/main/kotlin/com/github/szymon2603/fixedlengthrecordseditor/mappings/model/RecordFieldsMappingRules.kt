package com.github.szymon2603.fixedlengthrecordseditor.mappings.model

data class RecordFieldsMapping(
    val name: String = "",
    val fieldDescriptors: List<RecordFieldDescriptor> = emptyList()
) {
    val recordLength: Int by lazy { fieldDescriptors.maxBy { it.length }?.length ?: -1 }
}

data class RecordFieldDescriptor(
    val name: String = "",
    val startIndex: Int = 0,
    val endIndex: Int = 1,
    val alignment: RecordValueAlignment = RecordValueAlignment.LEFT
) {
    val length: Int
        get() = endIndex - startIndex

    fun copyWithNewLength(length: Int): RecordFieldDescriptor {
        return this.copy(endIndex = startIndex + length)
    }
}

enum class RecordValueAlignment {
    LEFT,
    CENTER,
    RIGHT
}
