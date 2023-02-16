package com.parking.main.models.feemodels

import com.parking.main.models.FeeModel
import com.parking.main.models.Interval

class AirportTwoWheelerFee() : FeeModel(){
    override var intervals: List<Interval> = listOf(Interval(0, 1), Interval(1, 8), Interval(8, 24), Interval(24, -1))
    override var rates: List<Long> = listOf(0, 40, 60, 80)
}

class AirportFourWheelerFee() : FeeModel(){
    override var intervals: List<Interval> = listOf(Interval(0, 4), Interval(4, 12), Interval(12, -1))
    override var rates: List<Long> = listOf(60, 120, 200)
}

class AirportHeavyVehicleFee() : FeeModel(){
    override var intervals: List<Interval> = listOf()
    override var rates: List<Long> = listOf()
}