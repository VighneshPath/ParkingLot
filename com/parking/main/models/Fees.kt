package com.parking.main.models

data class Interval(val start: Long, val end: Long)

class FeeModel(private val intervals: List<Interval>, private val rates: List<Long>) {
    fun getIntervals(): List<Interval> {
        return intervals
    }

    fun getRates(): List<Long> {
        return rates
    }
}