package com.github.szymon2603.fixedlengthrecordseditor.mappings

import com.github.szymon2603.fixedlengthrecordseditor.mappings.components.RecordFieldsMappingsStore
import com.github.szymon2603.fixedlengthrecordseditor.mappings.ui.RecordFieldsMappingsFormController
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.project.Project
import javax.swing.JComponent

class FixedLengthRecordsMappingConfigProvider(private val project: Project) : SearchableConfigurable {

    private lateinit var form: RecordFieldsMappingsFormController

    override fun createComponent(): JComponent? {
        val mappings = RecordFieldsMappingsStore.getInstance(project).mappings
        form = RecordFieldsMappingsFormController(mappings)
        return form.mainComponent
    }

    override fun isModified(): Boolean {
        val current = RecordFieldsMappingsStore.getInstance(project).mappings
        return if (::form.isInitialized) current != form.state else false
    }

    override fun apply() {
        RecordFieldsMappingsStore.getInstance(project).update(form.state)
    }

    override fun getDisplayName(): String {
        return "Fixed Length Records Mappings"
    }

    override fun getId(): String {
        return "fixed-length-records-mapping-configurable"
    }
}
