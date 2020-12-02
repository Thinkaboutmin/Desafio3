package com.trab.desafio3.helper

// Cherry picked https://www.javacodemonk.com/md5-and-sha256-in-java-kotlin-and-android-96ed9628

import java.security.MessageDigest

fun String.toMD5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return bytes.toHex()
}

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}
