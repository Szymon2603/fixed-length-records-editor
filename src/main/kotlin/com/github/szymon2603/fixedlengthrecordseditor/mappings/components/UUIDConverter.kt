package com.github.szymon2603.fixedlengthrecordseditor.mappings.components

import com.intellij.util.xmlb.Converter
import java.util.UUID

class UUIDConverter : Converter<UUID>() {

    override fun toString(value: UUID): String? = value.toString()

    override fun fromString(value: String): UUID? = UUID.fromString(value)
}
