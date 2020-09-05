package com.github.szymon2603.fixedlengthrecordseditor.model

import java.lang.IllegalArgumentException

data class RecordFieldsConfig(
    val fileProjectPath: String,
    val recordFieldDescriptors: List<RecordFieldDescriptor>
) {
    val numberOfFields: Int = recordFieldDescriptors.size

    fun copyWithRecordFieldDescriptor(fieldDescriptor: RecordFieldDescriptor): RecordFieldsConfig {
        return this.copy(recordFieldDescriptors = recordFieldDescriptors.plus(fieldDescriptor))
    }

    fun copyWithRecordFieldDescriptor(index: Int, fieldDescriptor: RecordFieldDescriptor): RecordFieldsConfig {
        val newList = recordFieldDescriptors.toMutableList().apply {
            this[index] = fieldDescriptor
        }.toList()
        return this.copy(recordFieldDescriptors = newList)
    }

    fun copyWithoutRecordFieldDescriptor(fieldDescriptor: RecordFieldDescriptor): RecordFieldsConfig {
        return this.copy(recordFieldDescriptors = recordFieldDescriptors.minus(fieldDescriptor))
    }
}

data class RecordFieldDescriptor(
    val name: String = "",
    val startIndex: Int = 0,
    val endIndex: Int = 1,
    val length: Int = endIndex - startIndex,
    val alignment: RecordValueAlignment = RecordValueAlignment.LEFT
) {

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

    fun copy(attributeIndex: Int, value: Any?): RecordFieldDescriptor {
        return when (attributeIndex) {
            0 -> this.copy(name = value as String)
            1 -> this.copy(startIndex = value as Int)
            2 -> this.copyWithNewEndIndex(value as Int)
            3 -> this.copyWithNewLength(length = value as Int)
            4 -> this.copy(alignment = value as RecordValueAlignment)
            else -> throw IllegalArgumentException("Attribute index [$attributeIndex] is not correct")
        }
    }

    fun copyWithNewEndIndex(endIndex: Int): RecordFieldDescriptor {
        return this.copy(endIndex = endIndex, length = endIndex - startIndex)
    }

    fun copyWithNewLength(length: Int): RecordFieldDescriptor {
        return this.copy(length = length, endIndex = startIndex + length)
    }

    companion object {
        const val NUMBER_OF_CONFIG_ATTRIBUTES = 5
    }
}

enum class RecordValueAlignment {
    LEFT,
    CENTER,
    RIGHT
}
