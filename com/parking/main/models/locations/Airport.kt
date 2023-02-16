package com.parking.main.models.locations

import com.parking.main.models.*
import com.parking.main.models.feemodels.AirportFourWheelerFee
import com.parking.main.models.feemodels.AirportHeavyVehicleFee
import com.parking.main.models.feemodels.AirportTwoWheelerFee
import java.time.Duration
import java.time.LocalDateTime

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
        var feeModel: FeeModel = when (vehicle.getType()) {
            VehicleType.TWO_WHEELER -> {
                AirportTwoWheelerFee()
            }

            VehicleType.FOUR_WHEELER -> {
                AirportFourWheelerFee()
            }

            VehicleType.HEAVY_VEHICLE -> {
                AirportHeavyVehicleFee()

            }
        }
        val intervals = feeModel.getIntervalsForModel()
        val rates = feeModel.getRatesForModel()
        if(duration >= 24){

        }
        intervals.forEachIndexed{index, interval ->

        }
        val fareReturned = flatFare.compute(duration, intervals, rates)

        return fareReturned.finalFare + (timeFare.compute(
            duration,
            intervals.slice(fareReturned.lastIndex until intervals.size),
            rates.slice(fareReturned.lastIndex until intervals.size)
        ).finalFare / 24)
    }
}