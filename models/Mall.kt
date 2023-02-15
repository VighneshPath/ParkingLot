package models

import java.time.Duration
import java.time.LocalDateTime

object MallFeeModel {
    var twoWheelerFeeModel: FeeModel = FeeModel(listOf(Interval(-1, -1)), listOf(10))
    var fourWheelerFeeModel: FeeModel = FeeModel(listOf(Interval(-1, -1)), listOf(20))
    var heavyVehicleFeeModel: FeeModel = FeeModel(listOf(Interval(-1, -1)), listOf(10))
}

class Mall(override var vehicleSpotsLimit: Map<VehicleType, Long>) : Location() {
    private val mallFeeModel: MallFeeModel = MallFeeModel
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
        val finalFare = when (vehicle.getType()) {
            VehicleType.TWO_WHEELER -> {
                flatFare.compute(duration, mallFeeModel.twoWheelerFeeModel)
            }

            VehicleType.FOUR_WHEELER -> {
                flatFare.compute(duration, mallFeeModel.fourWheelerFeeModel)
            }

            VehicleType.HEAVY_VEHICLE -> {
                flatFare.compute(duration, mallFeeModel.heavyVehicleFeeModel)
            }
        }
        return finalFare
    }
}