package com.github.szymon2603.fixedlengthrecordseditor.filters

import com.github.szymon2603.fixedlengthrecordseditor.filetypes.FixedLengthRecordsLanguage
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.ex.EditorSettingsExternalizable
import com.intellij.openapi.editor.ex.EditorSettingsExternalizable.STRIP_TRAILING_SPACES_WHOLE
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase

class NoStripTrailingSpacesFilterFactoryTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String {
        return "./src/test/resources/filters"
    }

    override fun setUp() {
        super.setUp()
        val editorSettings = ApplicationManager.getApplication().getComponent(EditorSettingsExternalizable::class.java)
        editorSettings.stripTrailingSpaces = STRIP_TRAILING_SPACES_WHOLE
    }

    fun `test that lines are not stripped on save`() {
        // Given
        myFixture.configureByFiles("test-file.txt")
        val fileType = myFixture.file.virtualFile.fileType
        assertTrue((fileType as LanguageFileType).language.isKindOf(FixedLengthRecordsLanguage))
        val content = "123   "
        ApplicationManager.getApplication().runWriteAction {
            myFixture.editor.document.setText(content)
        }

        // When
        // com.intellij.ide.actions.SaveAllAction
        myFixture.performEditorAction("SaveAll")

        // Then
        val actual = myFixture.file.virtualFile.inputStream.bufferedReader().readText()
        TestCase.assertEquals("123   ", actual)
    }

    fun `test that lines are stripped on save when file is not FLR file`() {
        // Given
        myFixture.configureByFiles("test-file.html")
        val fileType = myFixture.file.virtualFile.fileType
        assertFalse((fileType as LanguageFileType).language.isKindOf(FixedLengthRecordsLanguage))
        val content = "123   "
        ApplicationManager.getApplication().runWriteAction {
            myFixture.editor.document.setText(content)
        }

        // When
        // com.intellij.ide.actions.SaveAllAction
        myFixture.performEditorAction("SaveAll")

        // Then
        val actual = myFixture.file.virtualFile.inputStream.bufferedReader().readText()
        TestCase.assertEquals("123", actual)
    }
}
