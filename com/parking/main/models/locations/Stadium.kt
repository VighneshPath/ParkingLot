package com.parking.main.models.locations

import com.parking.main.models.*
import com.parking.main.models.feemodels.StadiumFourWheelerFee
import com.parking.main.models.feemodels.StadiumHeavyVehicleFee
import com.parking.main.models.feemodels.StadiumTwoWheelerFee
import java.time.Duration
import java.time.LocalDateTime

class Stadium(override var vehicleSpotsLimit: Map<VehicleType, Long>) : Location() {
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
        val feeModel: FeeModel = when (vehicle.getType()) {
            VehicleType.TWO_WHEELER -> {
                StadiumTwoWheelerFee()
            }

            VehicleType.FOUR_WHEELER -> {
                StadiumFourWheelerFee()
            }

            VehicleType.HEAVY_VEHICLE -> {
                StadiumHeavyVehicleFee()
            }
        }
        val intervals = feeModel.getIntervalsForModel()
        val rates = feeModel.getRatesForModel()
        val fareReturned = flatFare.compute(duration, intervals, rates)

        return fareReturned.finalFare + timeFare.compute(
            duration,
            intervals.slice(fareReturned.lastIndex until intervals.size),
            rates.slice(fareReturned.lastIndex until intervals.size)
        ).finalFare
    }
}