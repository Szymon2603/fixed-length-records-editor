package com.github.szymon2603.fixedlengthrecordseditor.model

import java.util.UUID

data class RecordFieldsMappingLinks(
    val mappingLinks: List<RecordFieldsMappingFileLink>
)

data class RecordFieldsMappingFileLink(
    val fileProjectPath: String,
    val mappingId: UUID
)
