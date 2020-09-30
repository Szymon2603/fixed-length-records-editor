package com.github.szymon2603.fixedlengthrecordseditor.mappings

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase

class FixedLengthRecordsMappingConfigProviderTest : BasePlatformTestCase() {

    private lateinit var configProvider: FixedLengthRecordsMappingConfigProvider

    override fun setUp() {
        super.setUp()
        configProvider = FixedLengthRecordsMappingConfigProvider(project)
    }

    fun `test that component is created`() {
        TestCase.assertNotNull(configProvider.createComponent())
    }

    fun `test that display name is not blank`() {
        TestCase.assertTrue(configProvider.displayName.trim() != "")
    }

    fun `test that id is not blank`() {
        TestCase.assertTrue(configProvider.id.trim() != "")
    }
}
