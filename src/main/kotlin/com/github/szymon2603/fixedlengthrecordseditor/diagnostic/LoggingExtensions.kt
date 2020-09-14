package com.github.szymon2603.fixedlengthrecordseditor.diagnostic

import com.intellij.openapi.diagnostic.Logger
import kotlin.reflect.KClass

fun <T : Any> KClass<T>.createLogger(): Logger {
    return Logger.getInstance(this::class.java)
}
