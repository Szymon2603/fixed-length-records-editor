package com.github.szymon2603.fixedlengthrecordseditor.mappings.components

import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldsMappingFileLink
import org.junit.Assert
import org.junit.Test
import java.util.UUID

internal class RecordFieldsMappingLinksStoreTest {

    @Test
    fun `test that default state is empty list`() {
        val component = RecordFieldsMappingLinksStore()
        val actual = component.mappingLinks

        Assert.assertEquals(emptyList<RecordFieldsMappingFileLink>(), actual)
    }

    @Test
    fun `test that add method adds new item`() {
        val component = RecordFieldsMappingLinksStore()
        val id = UUID.randomUUID()
        component.addLink(RecordFieldsMappingFileLink("file.txt", id))
        val actual = component.mappingLinks

        Assert.assertEquals(listOf(RecordFieldsMappingFileLink("file.txt", id)), actual)
    }

    @Test
    fun `test that remove method remove link`() {
        val component = RecordFieldsMappingLinksStore()
        val id1 = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        component.addLink(RecordFieldsMappingFileLink("file1.txt", id1))
        component.addLink(RecordFieldsMappingFileLink("file2.txt", id2))
        component.removeLink(RecordFieldsMappingFileLink("file1.txt", id1))

        val actual = component.mappingLinks
        Assert.assertEquals(listOf(RecordFieldsMappingFileLink("file2.txt", id2)), actual)
    }

    @Test
    fun `test load state convert model`() {
        val component = RecordFieldsMappingLinksStore()

        val id = UUID.randomUUID()
        val link = RecordFieldsMappingFileLinkElement("file.txt", id)
        val state = RecordFieldsMappingLinksState(listOf(link))

        component.loadState(state)
        val actual = component.mappingLinks
        Assert.assertEquals(listOf(RecordFieldsMappingFileLink("file.txt", id)), actual)
    }
}
