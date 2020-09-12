package com.github.szymon2603.fixedlengthrecordseditor.components

import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldsMappingFileLink
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

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

    fun addLink(link: RecordFieldsMappingFileLink) {
        synchronized(state) {
            val mappingLinks = state.mappingLinks.plus(RecordFieldsMappingFileLinkElement(link))
            state = state.copy(mappingLinks = mappingLinks)
        }
    }

    fun removeLink(link: RecordFieldsMappingFileLink) {
        synchronized(state) {
            if (state.mappingLinks.contains(RecordFieldsMappingFileLinkElement(link))) {
                val mappingLinks = state.mappingLinks.minus(RecordFieldsMappingFileLinkElement(link))
                state = state.copy(mappingLinks = mappingLinks)
            }
        }
    }
}
