package com.ksb.ksb_with_shiro.util

import org.apache.shiro.codec.Hex
import java.security.MessageDigest

object MD5Util {

    fun md5(source: String): String {
        val md = MessageDigest.getInstance("MD5")
        val md5Bytes = md.digest(source.toByteArray())
        return Hex.encodeToString(md5Bytes)
    }
}
