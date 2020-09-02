package com.github.szymon2603.fixedlengthrecordseditor.ui

import com.github.szymon2603.fixedlengthrecordseditor.filetypes.FixedLengthRecordsLanguage
import com.intellij.openapi.fileEditor.ex.FileEditorProviderManager
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.testFramework.fixtures.BasePlatformTestCase

internal class FixedLengthRecordsEditorProviderTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String {
        return "./src/test/resources/ui"
    }

    override fun setUp() {
        super.setUp()
        myFixture.configureByFiles("test-file.txt")
        val fileType = myFixture.file.virtualFile.fileType
        assertTrue((fileType as LanguageFileType).language.isKindOf(FixedLengthRecordsLanguage))
    }

    fun `test file have registered text editor provider`() {
        val fileEditorProviders = FileEditorProviderManager
                .getInstance()
                .getProviders(project, myFixture.file.virtualFile)

        assertTrue(fileEditorProviders.any { it is FixedLengthRecordsEditorProvider })
    }

    fun `test editor provider has proper id`() {
        val fileEditorProviders = FileEditorProviderManager
                .getInstance()
                .getProviders(project, myFixture.file.virtualFile)
                .first { it is FixedLengthRecordsEditorProvider }

        assertEquals("fixed-length-records-editor-id", fileEditorProviders.editorTypeId)
    }
}