package com.github.szymon2603.fixedlengthrecordseditor.ui

import com.github.szymon2603.fixedlengthrecordseditor.filetypes.FixedLengthRecordsLanguage
import com.intellij.openapi.fileEditor.ex.FileEditorProviderManager
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.Key
import com.intellij.testFramework.fixtures.BasePlatformTestCase

internal class FixedLengthRecordsEditorTest : BasePlatformTestCase() {

    private val editor by lazy(LazyThreadSafetyMode.NONE) {
        val provider = FileEditorProviderManager
                .getInstance()
                .getProviders(project, myFixture.file.virtualFile)
                .first { it is FixedLengthRecordsEditorProvider } as FixedLengthRecordsEditorProvider
        provider.createEditor(project, myFixture.file.virtualFile)
    }

    override fun getTestDataPath(): String {
        return "./src/test/resources/ui"
    }

    override fun setUp() {
        super.setUp()
        myFixture.configureByFiles("test-file.txt")
        val fileType = myFixture.file.virtualFile.fileType
        assertTrue((fileType as LanguageFileType).language.isKindOf(FixedLengthRecordsLanguage))
    }

    fun `test that type id is right`() {
        assertEquals("Records editor", editor.name)
    }

    fun `test that user data is saved and read`() {
        val key: Key<String> = Key.create("Some key")
        editor.putUserData(key, "1")
        val value: String? = editor.getUserData(key)
        assertEquals("1", value)
    }
}