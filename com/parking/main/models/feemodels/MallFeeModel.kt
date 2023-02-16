package com.parking.main.models.feemodels

import com.parking.main.models.FeeModel
import com.parking.main.models.Interval

class MallTwoWheelerFee() : FeeModel(){
    override var intervals: List<Interval> = listOf(Interval(-1, -1))
    override var rates: List<Long> = listOf(10)
}

class MallFourWheelerFee() : FeeModel(){
    override var intervals: List<Interval> = listOf(Interval(-1, -1))
    override var rates: List<Long> = listOf(20)
}

class MallHeavyVehicleFee() : FeeModel(){
    override var intervals: List<Interval> = listOf(Interval(-1, -1))
    override var rates: List<Long> = listOf(10)
}