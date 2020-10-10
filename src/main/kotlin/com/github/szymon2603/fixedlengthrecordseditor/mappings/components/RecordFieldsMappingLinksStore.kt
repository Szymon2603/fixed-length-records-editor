package com.github.szymon2603.fixedlengthrecordseditor.mappings.components

import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldsMappingFileLink
import com.github.szymon2603.fixedlengthrecordseditor.withReplacedItemAt
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.xmlb.XmlSerializerUtil
import java.util.UUID

@State(
    name = "RecordFieldsMappingLinksStore",
    storages = [Storage(value = "flr-editor/records-fields-mappings-links.xml")]
)
class RecordFieldsMappingLinksStore : PersistentStateComponent<RecordFieldsMappingLinksState> {

    private var state = RecordFieldsMappingLinksState()
    val mappingLinks: List<RecordFieldsMappingFileLink>
        get() = state.convert().mappingLinks

    override fun getState(): RecordFieldsMappingLinksState? = state

    override fun loadState(state: RecordFieldsMappingLinksState) {
        XmlSerializerUtil.copyBean(state, this.state)
    }

    fun getLink(openedFile: VirtualFile): RecordFieldsMappingFileLink? {
        val path = openedFile.path
        return mappingLinks.find { it.fileProjectPath == path }
    }

    fun addLink(link: RecordFieldsMappingFileLink) {
        synchronized(state) {
            addLinkInternally(link)
        }
    }

    private fun addLinkInternally(link: RecordFieldsMappingFileLink) {
        val mappingLinks = state.mappingLinks.plus(RecordFieldsMappingFileLinkElement(link))
        state = state.copy(mappingLinks = mappingLinks)
    }

    fun updateLink(openedFile: VirtualFile, key: UUID) {
        val path = openedFile.path
        synchronized(state) {
            val index = mappingLinks.indexOfFirst { it.fileProjectPath == path }
            if (index != -1) {
                val currentMappingLinks = state.mappingLinks
                val newLink = RecordFieldsMappingFileLinkElement(path, key)
                state = state.copy(mappingLinks = currentMappingLinks.withReplacedItemAt(index, newLink))
            } else {
                addLinkInternally(RecordFieldsMappingFileLink(path, key))
            }
        }
    }

    fun removeLink(openedFile: VirtualFile) {
        val path = openedFile.path
        synchronized(state) {
            val link = mappingLinks.find { it.fileProjectPath == path }
            if (link != null) {
                removeLinkInternally(link)
            }
        }
    }

    fun removeLink(link: RecordFieldsMappingFileLink) {
        synchronized(state) {
            removeLinkInternally(link)
        }
    }

    private fun removeLinkInternally(link: RecordFieldsMappingFileLink) {
        if (state.mappingLinks.contains(RecordFieldsMappingFileLinkElement(link))) {
            val mappingLinks = state.mappingLinks.minus(RecordFieldsMappingFileLinkElement(link))
            state = state.copy(mappingLinks = mappingLinks)
        }
    }
}
