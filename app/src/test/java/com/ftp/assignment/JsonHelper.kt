package com.ftp.assignment

import java.io.InputStreamReader
import java.lang.StringBuilder

object JsonHelper {

    fun readFileResource(filename : String) : String {
        val inputStream = JsonHelper::class.java.getResourceAsStream(filename)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }

}