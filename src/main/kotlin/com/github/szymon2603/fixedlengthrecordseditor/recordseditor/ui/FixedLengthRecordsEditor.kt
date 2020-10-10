package com.github.szymon2603.fixedlengthrecordseditor.recordseditor.ui

import com.github.szymon2603.fixedlengthrecordseditor.mappings.components.RecordFieldsMappingLinksStore
import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.services.RecordsReader
import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.services.RecordsWriter
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.openapi.vfs.VirtualFile
import org.slf4j.LoggerFactory
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

abstract class FixedLengthRecordsEditor(
    protected val project: Project,
    protected val openedFile: VirtualFile
) : FileEditor, FileEditorLocation {

    private val propertyChangeSupport: PropertyChangeSupport by lazy { PropertyChangeSupport(this) }
    private val userDataHolder = UserDataHolderBase()

    protected val recordsReader: RecordsReader =
        ApplicationManager.getApplication().getService(RecordsReader::class.java)
    protected val recordsWriter: RecordsWriter =
        ApplicationManager.getApplication().getService(RecordsWriter::class.java)
    protected val mappingLinksStore: RecordFieldsMappingLinksStore =
        project.getService(RecordFieldsMappingLinksStore::class.java)

    protected val document: Document = FileDocumentManager.getInstance().getDocument(openedFile)!!

    override fun <T : Any?> getUserData(key: Key<T>): T? = userDataHolder.getUserData(key)

    override fun <T : Any?> putUserData(key: Key<T>, value: T?) = userDataHolder.putUserData(key, value)

    override fun dispose() {
        log.debug("Dispose action for ${this.javaClass}")
        this.deselectNotify()
    }

    override fun getName(): String = "Records editor"

    override fun isValid(): Boolean = openedFile.isValid

    override fun setState(state: FileEditorState) {
        log.debug("There is no editor state for ${this.javaClass}")
    }

    override fun addPropertyChangeListener(listener: PropertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(listener)
    }

    override fun removePropertyChangeListener(listener: PropertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(listener)
    }

    override fun getCurrentLocation(): FileEditorLocation? = this

    override fun compareTo(other: FileEditorLocation?): Int = 1

    override fun getEditor(): FileEditor = this

    companion object {
        private val log = LoggerFactory.getLogger(FixedLengthRecordsEditor::class.java)
    }
}
