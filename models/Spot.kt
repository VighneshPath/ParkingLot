package models

class Spot(private val vehicleType: VehicleType) {
    var isTaken = false
    var vehicle: Vehicle? = null

    fun getVehicleType(): VehicleType {
        return vehicleType
    }

    fun setVehicleToSpot(vehicle: Vehicle) {
        isTaken = true
        this.vehicle = vehicle
    }

    fun removeVehicleFromSpot() {
        isTaken = false
        this.vehicle = null
    }
}