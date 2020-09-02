package com.github.szymon2603.fixedlengthrecordseditor.filetypes

import com.intellij.lang.Language

object FixedLengthRecordsLanguage : Language("FLR", "text/plain") {
    override fun getDisplayName(): String = "Fixed length records language"
}
