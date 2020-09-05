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
            NAME_ATTRIBUTE_INDEX -> name
            START_INDEX_ATTRIBUTE_INDEX -> startIndex
            END_INDEX_ATTRIBUTE_INDEX -> endIndex
            LENGTH_ATTRIBUTE_INDEX -> length
            ALIGNMENT_ATTRIBUTE_INDEX -> alignment
            else -> throw IllegalArgumentException("Attribute index [$attributeIndex] is not correct")
        }
    }

    fun copy(attributeIndex: Int, value: Any?): RecordFieldDescriptor {
        return when (attributeIndex) {
            NAME_ATTRIBUTE_INDEX -> this.copy(name = value as String)
            START_INDEX_ATTRIBUTE_INDEX -> this.copy(startIndex = value as Int)
            END_INDEX_ATTRIBUTE_INDEX -> this.copyWithNewEndIndex(value as Int)
            LENGTH_ATTRIBUTE_INDEX -> this.copyWithNewLength(length = value as Int)
            ALIGNMENT_ATTRIBUTE_INDEX -> this.copy(alignment = value as RecordValueAlignment)
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

        private const val NAME_ATTRIBUTE_INDEX = 0
        private const val START_INDEX_ATTRIBUTE_INDEX = 1
        private const val END_INDEX_ATTRIBUTE_INDEX = 2
        private const val LENGTH_ATTRIBUTE_INDEX = 3
        private const val ALIGNMENT_ATTRIBUTE_INDEX = 4
    }
}

enum class RecordValueAlignment {
    LEFT,
    CENTER,
    RIGHT
}
