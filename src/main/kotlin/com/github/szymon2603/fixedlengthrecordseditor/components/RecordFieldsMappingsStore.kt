package com.github.szymon2603.fixedlengthrecordseditor.components

import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldsMapping
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
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

    fun addConfig(mapping: RecordFieldsMapping): UUID {
        val id = UUID.randomUUID()
        synchronized(state) {
            state = state.copy(
                mappings = state
                    .mappings
                    .plus(RecordFieldsMappingElement(id, mapping))
            )
        }
        return id
    }

    fun updateConfig(id: UUID, mapping: RecordFieldsMapping): UUID {
        return if (!state.mappings.any { it.id == id }) {
            addConfig(mapping)
        } else {
            synchronized(state) {
                val newConfigs = state
                    .mappings
                    .map { if (it.id == id) RecordFieldsMappingElement(id, mapping) else it }
                state = state.copy(mappings = newConfigs)
            }
            return id
        }
    }
}
