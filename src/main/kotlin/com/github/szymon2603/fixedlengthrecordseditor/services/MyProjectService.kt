package com.github.szymon2603.fixedlengthrecordseditor.services

import com.intellij.openapi.project.Project
import com.github.szymon2603.fixedlengthrecordseditor.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
