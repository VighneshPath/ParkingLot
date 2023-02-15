package models

abstract class Fare {
    abstract fun compute(duration: Long, feeModel: FeeModel): Long
}

class FlatFare : Fare() {
    override fun compute(duration: Long, feeModel: FeeModel): Long {
        if (duration <= 0) return 0
        var finalFare = 0L
        feeModel.getIntervals().forEachIndexed { index, interval ->
            if (interval.end != -1L && duration < interval.end) {
                finalFare = feeModel.getRates()[index]
            } else if (interval.end == -1L) {
                finalFare = feeModel.getRates()[index]
                return@forEachIndexed
            }
        }
        return finalFare
    }
}


class TimeFare : Fare() {
    override fun compute(duration: Long, feeModel: FeeModel): Long {
        if (duration <= 0) return 0
        var finalFare = 0L
        feeModel.getIntervals().forEachIndexed { index, interval ->
            if (interval.end != -1L && duration < interval.end) {
                finalFare += feeModel.getRates()[index]
            } else if (interval.end == -1L) {
                finalFare += feeModel.getRates()[index]
                return@forEachIndexed
            }
        }
        return finalFare
    }
}
