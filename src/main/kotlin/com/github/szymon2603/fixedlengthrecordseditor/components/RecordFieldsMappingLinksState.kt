package com.github.szymon2603.fixedlengthrecordseditor.components

import com.github.szymon2603.fixedlengthrecordseditor.components.converters.UUIDConverter
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldsMappingFileLink
import com.github.szymon2603.fixedlengthrecordseditor.model.RecordFieldsMappingLinks
import com.intellij.util.xmlb.annotations.Attribute
import com.intellij.util.xmlb.annotations.Tag
import com.intellij.util.xmlb.annotations.XCollection
import java.util.UUID

data class RecordFieldsMappingLinksState(
    @XCollection(propertyElementName = "RecordFieldsMappingLinks")
    val mappingLinks: List<RecordFieldsMappingFileLinkElement> = emptyList()
) {
    fun convert() = RecordFieldsMappingLinks(mappingLinks.map { it.convert() })
}

@Tag("Link")
data class RecordFieldsMappingFileLinkElement(
    @Attribute val fileProjectPath: String = "",
    @Attribute(converter = UUIDConverter::class)
    val mappingKey: UUID = UUID.fromString("")
) {
    constructor(model: RecordFieldsMappingFileLink) : this(model.fileProjectPath, model.mappingId)

    fun convert() = RecordFieldsMappingFileLink(fileProjectPath, mappingKey)
}
