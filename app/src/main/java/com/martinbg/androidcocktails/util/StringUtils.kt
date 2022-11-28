package com.martinbg.androidcocktails.util

import java.util.*

object StringUtils {
    fun String.capitalizeFirstChar(): String {
        return this.lowercase().replaceFirstChar {
            if (it.isLowerCase()) {
                it.titlecase(Locale.getDefault())
            } else {
                it.toString()
            }
        }
    }
}