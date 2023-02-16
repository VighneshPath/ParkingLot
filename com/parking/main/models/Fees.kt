package com.parking.main.models

data class Interval(val start: Long, val end: Long)

abstract class FeeModel() {
    abstract var intervals: List<Interval>
    abstract var rates: List<Long>

    fun getIntervalsForModel(): List<Interval>{
        return intervals
    }

    fun getRatesForModel(): List<Long> {
        return rates
    }
}