package com.github.szymon2603.fixedlengthrecordseditor.filetypes

import com.intellij.icons.AllIcons
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object FixedLengthRecordsFileType : LanguageFileType(FixedLengthRecordsLanguage, true) {
    override fun getIcon(): Icon = AllIcons.FileTypes.Text
    override fun getName() = "Fixed length records file"
    override fun getDefaultExtension() = "txt"
    override fun getDescription() = "Plain text with fixed length records"
}
