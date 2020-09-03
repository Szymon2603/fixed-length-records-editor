package com.github.szymon2603.fixedlengthrecordseditor.services

import com.github.szymon2603.fixedlengthrecordseditor.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
