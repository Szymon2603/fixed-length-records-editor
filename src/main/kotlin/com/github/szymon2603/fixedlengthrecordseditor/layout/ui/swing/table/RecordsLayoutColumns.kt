package com.github.szymon2603.fixedlengthrecordseditor.layout.ui.swing.table

import com.github.szymon2603.fixedlengthrecordseditor.layout.model.RecordsLayout
import com.intellij.util.ui.ColumnInfo

object RecordsLayoutColumns {
    object Name : ColumnInfo<RecordsLayout, String>("Layout") {
        override fun valueOf(item: RecordsLayout?): String? {
            return item?.name
        }

        override fun setValue(item: RecordsLayout?, value: String?) {
            item?.name = value ?: ""
        }

        override fun isCellEditable(item: RecordsLayout?): Boolean = true
    }
}