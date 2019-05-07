package com.yeuristic.pin_ondemand

interface PinGenerator {
    fun generatePin(length : Int) : String
}