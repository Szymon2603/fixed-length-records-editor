package com.github.szymon2603.fixedlengthrecordseditor.recordseditor.services

import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldsMapping
import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.model.Field
import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.model.Record
import com.intellij.openapi.editor.Document
import org.slf4j.LoggerFactory

class RecordsReader {

    fun readRecords(mapping: RecordFieldsMapping, document: Document): List<Record> {
        log.debug("Reading records from document")
        return if (document.textLength == 0) emptyList()
        else document
            .text
            .split(NORMALIZED_DOCUMENT_DELIMITER)
            .mapIndexed { lineIndex, line -> convertToRecord(mapping, lineIndex, line) }
            .toList()
    }

    private fun convertToRecord(mapping: RecordFieldsMapping, lineIndex: Int, line: String): Record {
        val recordLength = mapping.recordLength
        if (line.length > recordLength) {
            log.warn("Line number ${lineIndex + 1} is too long! Actual [${line.length}], expected [$recordLength]")
        }
        val fields = mapping.fieldDescriptors.map { desc ->
            val value = line.substring(desc.startIndex, desc.endIndex).trim()
            Field(value = value, fieldDescriptor = desc)
        }
        return Record(fields)
    }

    companion object {
        private val log = LoggerFactory.getLogger(RecordsReader::class.java)

        // From documentation - all texts in Document object have normalized end lines to "\n"
        // https://jetbrains.org/intellij/sdk/docs/basics/architectural_overview/documents.html
        private const val NORMALIZED_DOCUMENT_DELIMITER = "\n"
    }
}
