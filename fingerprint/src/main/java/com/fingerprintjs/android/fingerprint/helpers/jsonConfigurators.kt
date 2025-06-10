package com.fingerprintjs.android.fingerprint.helpers

import com.fingerprintjs.android.fingerprint.utils.Jsonifiable

public object Configurators {
    public fun toJsonMap(map: Map<String, Any?>): Map<String, Any?> {
        val result = mutableMapOf<String, Any?>()
        for ((key, value) in map) {
            result[key] = when (value) {
                null -> null
                is Jsonifiable -> toJsonMap(value.toJsonifiable() as Map<String, Any?>)
                is String -> value
                is Int -> value
                is Double -> value
                is Boolean -> value
                is List<*> -> toJsonList(value)
                is Map<*, *> -> toJsonMap(value as Map<String, Any?>)
                else -> value.toString()
            }
        }
        return result
    }

    public fun toJsonList(list: List<*>): List<Any?> {
        val result = mutableListOf<Any?>()
        for (item in list) {
            val convertedItem = when (item) {
                null -> null
                is Pair<*, *> -> {
                    val map = mapOf("key" to item.first, "value" to item.second)
                    toJsonMap(map)
                }
                is Jsonifiable -> {
                    val jsonValue = item.toJsonifiable()
                    if (jsonValue is Map<*, *>) {
                        toJsonMap(jsonValue as Map<String, Any?>)
                    } else {
                        convertValue(jsonValue)
                    }
                }
                is String -> item
                is Int -> item
                is Double -> item
                is Boolean -> item
                is List<*> -> toJsonList(item)
                is Map<*, *> -> toJsonMap(item as Map<String, Any?>)
                else -> item.toString()
            }
            result.add(convertedItem)
        }
        return result
    }

    private fun convertValue(value: Any): Any? {
        return when (value) {
            is String -> value
            is Int -> value
            is Double -> value
            is Boolean -> value
            is List<*> -> toJsonList(value)
            is Map<*, *> -> toJsonMap(value as Map<String, Any?>)
            else -> value.toString()
        }
    }
}