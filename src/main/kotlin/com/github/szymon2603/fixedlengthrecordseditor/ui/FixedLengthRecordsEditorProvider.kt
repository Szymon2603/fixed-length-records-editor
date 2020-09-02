package com.github.szymon2603.fixedlengthrecordseditor.ui

import com.github.szymon2603.fixedlengthrecordseditor.filetypes.FixedLengthRecordsLanguage
import com.intellij.openapi.fileEditor.AsyncFileEditorProvider
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorPolicy
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class FixedLengthRecordsEditorProvider : AsyncFileEditorProvider {

    override fun accept(project: Project, file: VirtualFile): Boolean {
        val fileType = file.fileType
        return (fileType as? LanguageFileType)?.language?.isKindOf(FixedLengthRecordsLanguage) ?: false
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        return createEditorAsync(project, file).build()
    }

    override fun getEditorTypeId(): String = "fixed-length-records-editor-id"

    override fun getPolicy(): FileEditorPolicy = FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR

    override fun createEditorAsync(project: Project, file: VirtualFile): AsyncFileEditorProvider.Builder {
        return object : AsyncFileEditorProvider.Builder() {
            override fun build(): FileEditor {
                return FixedLengthRecordsEditor()
            }
        }
    }

}