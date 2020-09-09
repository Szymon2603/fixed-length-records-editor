package com.github.szymon2603.fixedlengthrecordseditor.components

import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldsConfig
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "RecordFieldsConfigStore",
    storages = [Storage(value = "fixed-length-editor-records/records-fields-configs.xml")]

)
class RecordFieldsConfigStore : ProjectComponent, PersistentStateComponent<RecordFieldsConfigsElement> {

    private var mainElement: RecordFieldsConfigsElement = RecordFieldsConfigsElement()
    val configs: List<RecordFieldsConfig>
        get() = mainElement.configs.map { it.convert() }

    override fun getState(): RecordFieldsConfigsElement? = mainElement

    override fun loadState(element: RecordFieldsConfigsElement) {
        XmlSerializerUtil.copyBean(element, this.mainElement)
    }

    fun addConfig(config: RecordFieldsConfig) {
        synchronized(mainElement) {
            mainElement = mainElement.copy(configs = mainElement.configs.plus(RecordFieldsConfigElement(config)))
        }
    }

    fun updateConfig(config: RecordFieldsConfig) {
        val actual = mainElement.configs.indexOfFirst { it.fileProjectPath == config.fileProjectPath }
        if (actual == -1) {
            addConfig(config)
        } else {
            synchronized(mainElement) {
                val newConfigs = mainElement
                    .configs
                    .mapIndexed { i, v -> if (i == actual) RecordFieldsConfigElement(config) else v }
                mainElement = mainElement.copy(configs = newConfigs)
            }
        }
    }
}
