package com.parking.main.models.locations

import com.parking.main.models.*
import java.time.Duration
import java.time.LocalDateTime

object AirportFeeModel {
    var twoWheelerFeeModel: FeeModel =
        FeeModel(listOf(Interval(0, 1), Interval(1, 8), Interval(8, 24), Interval(24, -1)), listOf(0, 40, 60, 80))
    var fourWheelerFeeModel: FeeModel =
        FeeModel(listOf(Interval(0, 4), Interval(4, 12), Interval(12, -1)), listOf(60, 120, 200))
    var heavyVehicleFeeModel: FeeModel = FeeModel(listOf(), listOf())
}

class Airport(override var vehicleSpotsLimit: Map<VehicleType, Long>) : Location() {
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
                val intervals = StadiumFeeModel.twoWheelerFeeModel.getIntervals()
                val rates = StadiumFeeModel.twoWheelerFeeModel.getRates()
                val fareReturned = flatFare.compute(duration, intervals, rates)

                fareReturned.finalFare + (timeFare.compute(
                    duration,
                    intervals.slice(fareReturned.lastIndex until intervals.size),
                    rates.slice(fareReturned.lastIndex until intervals.size)
                ).finalFare/24)
            }

            VehicleType.FOUR_WHEELER -> {
                val intervals = StadiumFeeModel.fourWheelerFeeModel.getIntervals()
                val rates = StadiumFeeModel.fourWheelerFeeModel.getRates()
                val fareReturned = flatFare.compute(duration, intervals, rates)

                fareReturned.finalFare + (timeFare.compute(
                    duration,
                    intervals.slice(fareReturned.lastIndex until intervals.size),
                    rates.slice(fareReturned.lastIndex until intervals.size)
                ).finalFare/24)
            }

            VehicleType.HEAVY_VEHICLE -> {
                val intervals = StadiumFeeModel.heavyVehicleFeeModel.getIntervals()
                val rates = StadiumFeeModel.heavyVehicleFeeModel.getRates()
                val fareReturned = flatFare.compute(duration, intervals, rates)

                fareReturned.finalFare + (timeFare.compute(
                    duration,
                    intervals.slice(fareReturned.lastIndex until intervals.size),
                    rates.slice(fareReturned.lastIndex until intervals.size)
                ).finalFare/24)
            }
        }

        return finalFare
    }
}