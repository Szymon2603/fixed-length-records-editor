package com.github.szymon2603.fixedlengthrecordseditor.ui

import com.github.szymon2603.fixedlengthrecordseditor.ui.swing.FixedLengthRecordsForm
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.UserDataHolderBase
import org.slf4j.LoggerFactory
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import javax.swing.JComponent

class FixedLengthRecordsEditor : FileEditor, FileEditorLocation {

    private val log = LoggerFactory.getLogger(FixedLengthRecordsEditor::class.java)

    private val form = FixedLengthRecordsForm()
    private val propertyChangeSupport: PropertyChangeSupport by lazy { PropertyChangeSupport(this) }
    private val userDataHolder = UserDataHolderBase()

    override fun <T : Any?> getUserData(key: Key<T>): T? = userDataHolder.getUserData(key)

    override fun <T : Any?> putUserData(key: Key<T>, value: T?) = userDataHolder.putUserData(key, value)

    override fun dispose() {
        log.debug("Dispose action")
        this.deselectNotify()
    }

    override fun getComponent(): JComponent = form.mainPanel

    override fun getPreferredFocusedComponent(): JComponent? = form.tabbedPane

    override fun getName(): String = "Records editor"

    override fun setState(state: FileEditorState) {
        throw UnsupportedOperationException("That method is not supported for this editor")
    }

    override fun isModified(): Boolean = false

    override fun isValid(): Boolean = file != null && file!!.isValid

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