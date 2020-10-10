package com.github.szymon2603.fixedlengthrecordseditor.mappings.components

import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldsMappingFileLink
import com.intellij.mock.MockVirtualFile
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
    fun `test that remove method removes link by RecordFieldsMappingFileLink object`() {
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
    fun `test that remove method removes link by VirtualFile object`() {
        val component = RecordFieldsMappingLinksStore()
        val virtualFile1 = MockVirtualFile("file1.txt")
        val virtualFile2 = MockVirtualFile("file2.txt")
        val id1 = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        component.addLink(RecordFieldsMappingFileLink(virtualFile1.path, id1))
        component.addLink(RecordFieldsMappingFileLink(virtualFile2.path, id2))
        component.removeLink(virtualFile1)

        val actual = component.mappingLinks
        Assert.assertEquals(listOf(RecordFieldsMappingFileLink(virtualFile2.path, id2)), actual)
    }

    @Test
    fun `test load state converts model`() {
        val component = RecordFieldsMappingLinksStore()

        val id = UUID.randomUUID()
        val link = RecordFieldsMappingFileLinkElement("file.txt", id)
        val state = RecordFieldsMappingLinksState(listOf(link))

        component.loadState(state)
        val actual = component.mappingLinks
        Assert.assertEquals(listOf(RecordFieldsMappingFileLink("file.txt", id)), actual)
    }

    @Test
    fun `test get link returns link for file`() {
        val virtualFile = MockVirtualFile("file.txt")
        val component = RecordFieldsMappingLinksStore()

        val id = UUID.randomUUID()
        component.addLink(RecordFieldsMappingFileLink(virtualFile.path, id))

        val actual = component.getLink(virtualFile)
        Assert.assertEquals(RecordFieldsMappingFileLink(virtualFile.path, id), actual)
    }

    @Test
    fun `test update link adds new link if it doesn't exist`() {
        val virtualFile = MockVirtualFile("file.txt")
        val component = RecordFieldsMappingLinksStore()

        val id = UUID.randomUUID()
        component.updateLink(virtualFile, id)

        val actual = component.mappingLinks
        Assert.assertEquals(listOf(RecordFieldsMappingFileLink(virtualFile.path, id)), actual)
    }

    @Test
    fun `test update link updates existing link`() {
        val component = RecordFieldsMappingLinksStore()
        val virtualFile1 = MockVirtualFile("file1.txt")
        val virtualFile2 = MockVirtualFile("file2.txt")
        val id1 = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        component.addLink(RecordFieldsMappingFileLink(virtualFile1.path, id1))
        component.addLink(RecordFieldsMappingFileLink(virtualFile2.path, id2))

        val id = UUID.randomUUID()
        component.updateLink(virtualFile1, id)

        val actual = component.mappingLinks
        val expected = listOf(
            RecordFieldsMappingFileLink(virtualFile1.path, id),
            RecordFieldsMappingFileLink(virtualFile2.path, id2)
        )
        Assert.assertEquals(expected, actual)
    }
}
