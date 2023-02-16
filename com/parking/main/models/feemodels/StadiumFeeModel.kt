package com.parking.main.models.feemodels

import com.parking.main.models.FeeModel
import com.parking.main.models.Interval

class StadiumTwoWheelerFee() : FeeModel(){
    override var intervals: List<Interval> = listOf(Interval(0, 4), Interval(4, 12), Interval(12, -1))
    override var rates: List<Long> = listOf(30, 60, 100)
}

class StadiumFourWheelerFee() : FeeModel(){
    override var intervals: List<Interval> = listOf(Interval(0, 4), Interval(4, 12), Interval(12, -1))
    override var rates: List<Long> = listOf(60, 120, 200)
}

class StadiumHeavyVehicleFee() : FeeModel(){
    override var intervals: List<Interval> = listOf()
    override var rates: List<Long> = listOf()
}