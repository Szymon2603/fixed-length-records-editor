package com.github.szymon2603.fixedlengthrecordseditor

fun <K, V> Map<K, V>.withReplacedItemAt(key: K, value: V): Map<K, V> {
    return this.toMutableMap().apply {
        this[key] = value
    }.toMap()
}

fun <E> List<E>.withReplacedItemAt(index: Int, value: E): List<E> {
    return this.toMutableList().apply {
        this[index] = value
    }.toList()
}

fun <E> List<E>.minusWithIndex(index: Int): List<E> {
    return this.filterIndexed { i, _ -> i != index }
}
