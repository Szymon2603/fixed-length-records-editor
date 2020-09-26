package com.github.szymon2603.fixedlengthrecordseditor.recordseditor.ui

import com.github.szymon2603.fixedlengthrecordseditor.diagnostic.createLogger
import com.github.szymon2603.fixedlengthrecordseditor.mappings.components.RecordFieldsMappingLinksStore
import com.github.szymon2603.fixedlengthrecordseditor.mappings.components.RecordFieldsMappingsStore
import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldsMapping
import com.github.szymon2603.fixedlengthrecordseditor.recordsecitor.ui.swing.FixedLengthRecordsForm
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.openapi.vfs.VirtualFile
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import javax.swing.JComponent

@Suppress("TooManyFunctions")
class FixedLengthRecordsEditor(
    project: Project,
    private val openedFile: VirtualFile
) : FileEditor, FileEditorLocation {

    private val log = FixedLengthRecordsEditor::class.createLogger()

    private val form = FixedLengthRecordsForm()
    private val propertyChangeSupport: PropertyChangeSupport by lazy { PropertyChangeSupport(this) }
    private val userDataHolder = UserDataHolderBase()

    private var mapping: RecordFieldsMapping? = null
    private var noMappingFound: Boolean = false

    init {
        val mappingsLinksStore = project.getComponent(RecordFieldsMappingLinksStore::class.java)
        val link = mappingsLinksStore.mappingLinks.find { it.fileProjectPath == openedFile.path }
        if (link == null) {
            noMappingFound = true
        } else {
            val recordFieldsMappingsStore = project.getComponent(RecordFieldsMappingsStore::class.java)
            mapping = requireNotNull(recordFieldsMappingsStore.mappings[link.mappingId])
        }
    }

    override fun <T : Any?> getUserData(key: Key<T>): T? = userDataHolder.getUserData(key)

    override fun <T : Any?> putUserData(key: Key<T>, value: T?) = userDataHolder.putUserData(key, value)

    override fun dispose() {
        log.debug("Dispose action")
        this.deselectNotify()
    }

    override fun getComponent(): JComponent = form.mainPanel

    override fun getPreferredFocusedComponent(): JComponent? = form.mainPanel

    override fun getName(): String = "Records editor"

    override fun setState(state: FileEditorState) {
//        throw UnsupportedOperationException("That method is not supported for this editor")
    }

    override fun isModified(): Boolean = false

//    override fun isValid(): Boolean = file != null && file!!.isValid
    override fun isValid(): Boolean = true

    override fun addPropertyChangeListener(listener: PropertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(listener)
    }

    override fun removePropertyChangeListener(listener: PropertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(listener)
    }

    override fun getCurrentLocation(): FileEditorLocation? = this

    override fun compareTo(other: FileEditorLocation?): Int = 1

    override fun getEditor(): FileEditor = this
}
