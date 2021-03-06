package com.github.szymon2603.fixedlengthrecordseditor.recordseditor.services

import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.model.Record
import com.intellij.openapi.application.ApplicationManager
import com.intellij.psi.PsiFile
import org.slf4j.LoggerFactory

class RecordsWriter {

    private val log = LoggerFactory.getLogger(RecordsReader::class.java)

    fun writeRecords(psiFile: PsiFile, records: List<Record>) {
        val file = psiFile.virtualFile
        log.debug("Writing records to file ${file.path}")
        val separator = file.detectedLineSeparator ?: System.lineSeparator()
        log.debug("Line separator ${encodeLineSeparator(separator)}")
        val content = records.joinToString(separator = separator, postfix = separator) {
            it.convertToString()
        }.toByteArray()
        ApplicationManager.getApplication().runWriteAction() {
            file.setBinaryContent(content)
        }
    }

    private fun encodeLineSeparator(separator: String): String {
        return when (separator) {
            "\n" -> "LF"
            "\r\n" -> "CRLF"
            "\r" -> "CR"
            else -> "Unknown separator [$separator]"
        }
    }
}
