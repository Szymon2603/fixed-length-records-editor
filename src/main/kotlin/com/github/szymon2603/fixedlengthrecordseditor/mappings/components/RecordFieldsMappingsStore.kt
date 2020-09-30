package com.github.szymon2603.fixedlengthrecordseditor.mappings.components

import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldsMapping
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import java.util.UUID

@State(
    name = "RecordFieldsMappingsStore",
    storages = [Storage(value = "flr-editor/records-fields-mappings.xml")]
)
class RecordFieldsMappingsStore : PersistentStateComponent<RecordFieldsMappingsState> {

    private var state: RecordFieldsMappingsState = RecordFieldsMappingsState()
    val mappings: Map<UUID, RecordFieldsMapping>
        get() = state.mappings.map { it.id to it.convert() }.toMap()

    override fun getState(): RecordFieldsMappingsState? = state

    override fun loadState(state: RecordFieldsMappingsState) {
        XmlSerializerUtil.copyBean(state, this.state)
    }

    fun update(mappings: Map<UUID, RecordFieldsMapping>) {
        synchronized(state) {
            val newMappings = mappings.map { RecordFieldsMappingElement(it.key, it.value) }
            state = state.copy(mappings = newMappings)
        }
    }

    companion object {
        fun getInstance(project: Project): RecordFieldsMappingsStore {
            return ServiceManager.getService(project, RecordFieldsMappingsStore::class.java)
        }
    }
}
