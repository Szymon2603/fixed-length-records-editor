package com.github.szymon2603.fixedlengthrecordseditor.services

import com.github.szymon2603.fixedlengthrecordseditor.diagnostic.createLogger
import com.github.szymon2603.fixedlengthrecordseditor.model.Field
import com.github.szymon2603.fixedlengthrecordseditor.model.Record
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldsMapping
import com.intellij.psi.PsiFile

class RecordsReader {

    private val log = RecordsReader::class.createLogger()

    fun readRecords(mapping: RecordFieldsMapping, psiFile: PsiFile): List<Record> {
        val file = psiFile.virtualFile
        log.debug("Reading records from ${file.path}")
        return file.inputStream
            .bufferedReader()
            .useLines { lines ->
                lines
                    .mapIndexed { lineIndex, line -> convertToRecord(mapping, lineIndex, line) }
                    .toList()
            }
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
}
