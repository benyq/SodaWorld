package com.benyq.sodaworld.base.extensions

import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException

/**
 *
 * @author benyq
 * @date 7/14/2023
 *
 */

fun String.isJson(): Boolean {
    return try {
        val jsonElement = JsonParser.parseString(this)
        jsonElement.isJsonObject
    }catch (e: JsonSyntaxException){
        false
    }
}

fun <T> String.toNumberDefault(default: T) : T {
    return try {
        val res: Any = when (default) {
            is Long -> toLong()
            is Int -> toInt()
            is Float -> toFloat()
            is Double -> toDouble()
            is Short -> toShort()
            else ->  throw NumberFormatException("未找到该类型")
        }
        res as T
    }catch (e: NumberFormatException) {
        default
    }
}