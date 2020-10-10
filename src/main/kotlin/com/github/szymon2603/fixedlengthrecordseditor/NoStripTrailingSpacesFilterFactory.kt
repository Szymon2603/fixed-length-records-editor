package com.github.szymon2603.fixedlengthrecordseditor

import com.github.szymon2603.fixedlengthrecordseditor.filetypes.FixedLengthRecordsFileType
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.StripTrailingSpacesFilter
import com.intellij.openapi.editor.StripTrailingSpacesFilterFactory
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import org.slf4j.LoggerFactory

class NoStripTrailingSpacesFilterFactory : StripTrailingSpacesFilterFactory() {

    override fun createFilter(project: Project?, document: Document): StripTrailingSpacesFilter {
        val file = FileDocumentManager.getInstance().getFile(document)
        val shouldNotStripLines = file?.fileType == FixedLengthRecordsFileType
        log.debug("Should not strip lines [$shouldNotStripLines] for file ${file?.path}")
        return if (shouldNotStripLines) StripTrailingSpacesFilter.NOT_ALLOWED
        else StripTrailingSpacesFilter.ENFORCED_REMOVAL
    }

    companion object {
        private val log = LoggerFactory.getLogger(NoStripTrailingSpacesFilterFactory::class.java)
    }
}
