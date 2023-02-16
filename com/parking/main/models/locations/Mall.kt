package com.parking.main.models.locations

import com.parking.main.models.*
import com.parking.main.models.feemodels.MallFourWheelerFee
import com.parking.main.models.feemodels.MallHeavyVehicleFee
import com.parking.main.models.feemodels.MallTwoWheelerFee
import java.time.Duration
import java.time.LocalDateTime

class Mall(override var vehicleSpotsLimit: Map<VehicleType, Long>) : Location() {
    private val flatFare: FlatFare = FlatFare()

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
                MallTwoWheelerFee()
            }

            VehicleType.FOUR_WHEELER -> {
                MallFourWheelerFee()
            }

            VehicleType.HEAVY_VEHICLE -> {
                MallHeavyVehicleFee()
            }
        }
        val intervals = feeModel.getIntervalsForModel()
        val rates = feeModel.getRatesForModel()

        return flatFare.compute(duration, intervals, rates).finalFare
    }
}