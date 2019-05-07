package com.yeuristic.password_included

interface PasswordGenerator {
    fun generatePassword(length : Int, vararg charsetList: Set<Char>) : String
}