package com.parking.main.models

import java.time.Duration
import java.time.LocalDateTime

object StadiumFeeModel {
    var twoWheelerFeeModel: FeeModel =
        FeeModel(listOf(Interval(0, 4), Interval(4, 12), Interval(12, -1)), listOf(30, 60, 100))
    var fourWheelerFeeModel: FeeModel =
        FeeModel(listOf(Interval(0, 4), Interval(4, 12), Interval(12, -1)), listOf(60, 120, 200))
    var heavyVehicleFeeModel: FeeModel = FeeModel(listOf(), listOf())
}

class Stadium(override var vehicleSpotsLimit: Map<VehicleType, Long>) : Location() {
    private val stadiumFeeModel: StadiumFeeModel = StadiumFeeModel
    private val flatFare: FlatFare = FlatFare()
    private val timeFare: TimeFare = TimeFare()

    init {
        currentVehicleTypeSpots[VehicleType.TWO_WHEELER] = 0
        currentVehicleTypeSpots[VehicleType.FOUR_WHEELER] = 0
        currentVehicleTypeSpots[VehicleType.HEAVY_VEHICLE] = 0

        for (spotForCarType in vehicleSpotsLimit) {
            for (j in 0..spotForCarType.value) {
                currentSpots[totalVehicleCapacity] = Spot(spotForCarType.key)
                totalVehicleCapacity += 1
            }
        }
    }


    override fun getFeeToPay(vehicle: Vehicle): Long {
        val duration = Duration.between(vehicle.getTicketStartTime(), LocalDateTime.now()).toHours()
        val finalFare = when (vehicle.getType()) {
            VehicleType.TWO_WHEELER -> {
                flatFare.compute(duration, StadiumFeeModel.twoWheelerFeeModel) + timeFare.compute(
                    duration,
                    StadiumFeeModel.twoWheelerFeeModel
                )
            }

            VehicleType.FOUR_WHEELER -> {
                flatFare.compute(duration, StadiumFeeModel.fourWheelerFeeModel) + timeFare.compute(
                    duration,
                    StadiumFeeModel.twoWheelerFeeModel
                )
            }

            VehicleType.HEAVY_VEHICLE -> {
                flatFare.compute(duration, StadiumFeeModel.heavyVehicleFeeModel) + timeFare.compute(
                    duration,
                    StadiumFeeModel.twoWheelerFeeModel
                )
            }
        }

        return finalFare
    }
}