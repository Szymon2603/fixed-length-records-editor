package com.github.szymon2603.fixedlengthrecordseditor.listeners

import com.github.szymon2603.fixedlengthrecordseditor.services.MyProjectService
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.getService(MyProjectService::class.java)
    }
}
