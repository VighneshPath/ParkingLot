package models

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
    private val airportFeeModel: AirportFeeModel = AirportFeeModel
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
                flatFare.compute(duration, airportFeeModel.twoWheelerFeeModel) + timeFare.compute(
                    (duration - 24) / 24,
                    airportFeeModel.twoWheelerFeeModel
                )
            }

            VehicleType.FOUR_WHEELER -> {
                flatFare.compute(duration, airportFeeModel.fourWheelerFeeModel) + timeFare.compute(
                    (duration - 24) / 24,
                    airportFeeModel.twoWheelerFeeModel
                )
            }

            VehicleType.HEAVY_VEHICLE -> {
                flatFare.compute(
                    duration,
                    airportFeeModel.heavyVehicleFeeModel
                ) + timeFare.compute((duration - 24) / 24, airportFeeModel.twoWheelerFeeModel)
            }
        }

        return finalFare
    }
}