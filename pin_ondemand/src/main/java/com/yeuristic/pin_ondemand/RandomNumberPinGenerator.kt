package com.yeuristic.pin_ondemand

import java.util.*

class RandomNumberPinGenerator : PinGenerator {
    private val random = Random()
    override fun generatePin(length: Int) : String {
        val randomInt = random.nextInt(Math.pow(10.toDouble(), length.toDouble()).toInt())
        return String.format("%0${length}d", randomInt)
    }
}