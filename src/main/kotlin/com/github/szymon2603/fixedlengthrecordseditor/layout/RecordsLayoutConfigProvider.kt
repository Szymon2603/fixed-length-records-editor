package com.github.szymon2603.fixedlengthrecordseditor.layout

import com.github.szymon2603.fixedlengthrecordseditor.layout.model.FieldMapping
import com.github.szymon2603.fixedlengthrecordseditor.layout.model.RecordMapping
import com.github.szymon2603.fixedlengthrecordseditor.layout.model.RecordsLayout
import com.github.szymon2603.fixedlengthrecordseditor.layout.ui.swing.RecordsLayoutConfigForm
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.project.Project
import javax.swing.JComponent

class RecordsLayoutConfigProvider(private val project: Project) : SearchableConfigurable {

    // TODO: Has to be loaded from proper service
    private val layouts: MutableList<RecordsLayout> = mutableListOf(
        RecordsLayout("layout one", mutableListOf(
            RecordMapping("mapping one", mutableListOf(
                FieldMapping("field one")
            ))
        ))
    )

    override fun createComponent(): JComponent? {
        return RecordsLayoutConfigForm(layouts).mainPanel
    }

    override fun isModified(): Boolean {
        return true
    }

    override fun apply() {

    }

    override fun getDisplayName(): String {
        return "Fixed Length Records Layouts"
    }

    override fun getId(): String {
        return "fixed-length-records-layouts-configurable"
    }
}