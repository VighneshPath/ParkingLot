package com.parking.main.models

abstract class Fare {
    abstract fun compute(duration: Long, intervals: List<Interval>, rates: List<Long>): RateReturned
}

data class RateReturned(val finalFare: Long, val lastIndex: Int)
class FlatFare : Fare() {
    override fun compute(duration: Long, intervals: List<Interval>, rates: List<Long>): RateReturned {
        if (duration <= 0) return RateReturned(0,0)
        var finalFare = 0L
        var index = 0
        for(anInterval in intervals.indices){
            index = anInterval
            val interval = intervals[anInterval]
            if (interval.end != -1L && duration < interval.end) {
                finalFare = rates[anInterval]
            } else if (interval.end == -1L) {
                finalFare = rates[anInterval]
                break
            }
        }
        return RateReturned(finalFare, index)
    }
}


class TimeFare : Fare() {
    override fun compute(duration: Long, intervals: List<Interval>, rates: List<Long>): RateReturned {
        if (duration <= 0) return RateReturned(0,0)
        var finalFare = 0L
        var index = 0
        for(anInterval in intervals.indices){
            index = anInterval
            val interval = intervals[anInterval]
            if (interval.end != -1L && duration < interval.end) {
                finalFare += rates[anInterval]*(interval.end-interval.start)
            } else if (interval.end == -1L) {
                finalFare += rates[anInterval]*(duration-interval.start)
                break
            }
        }
        return RateReturned(finalFare, index)
    }
}
