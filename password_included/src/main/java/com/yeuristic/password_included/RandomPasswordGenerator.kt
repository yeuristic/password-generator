package com.yeuristic.password_included

import java.util.*

class RandomPasswordGenerator : PasswordGenerator {
    private val random = Random()
    override fun generatePassword(length: Int, vararg charsetList: Set<Char>): String {

        var result = ""
        var charsetIdx = 0
        var charset: List<Char>

        while (result.length < length) {
            charsetIdx %= charsetList.size
            charset = charsetList[charsetIdx].toList()
            val randomIndex = random.nextInt(charset.size)
            result += charset[randomIndex]
            charsetIdx++
        }

        return result.toCharArray().toMutableList()
            .shuffled(random)
            .toTypedArray()
            .toCharArray().toNewString()
    }

    private fun CharArray.toNewString() = String(this)
}