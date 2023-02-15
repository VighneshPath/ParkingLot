package com.parking.main.models

import java.time.LocalDateTime

abstract class Location {
    var totalVehicleCapacity: Long = 0
    private var ticketCounter: Long = 0
    private var receiptCounter: Long = 0

    open lateinit var vehicleSpotsLimit: Map<VehicleType, Long>
    open var currentVehicleTypeSpots: MutableMap<VehicleType, Long> = mutableMapOf()

    open var currentSpots: MutableMap<Long, Spot> = mutableMapOf()

    private fun isParkingAvailable(spotNumber: Long): Boolean {
        return !currentSpots[spotNumber]!!.isTaken
    }

    private fun spotFor(spotNumber: Long, vehicleType: VehicleType): Boolean {
        return currentSpots[spotNumber]!!.getVehicleType() == vehicleType
    }

    private fun getNextSpot(vehicleType: VehicleType): Long {
        for (spot in 1..totalVehicleCapacity) {
            if (isParkingAvailable(spot) && spotFor(spot, vehicleType)) {
                return spot
            }
        }
        return -1
    }

    private fun reserveSpot(spot: Long, vehicle: Vehicle): Boolean {
        currentSpots[spot]!!.setVehicleToSpot(vehicle)
        return true
    }


    private fun isSpotAvailableFor(vehicleType: VehicleType): Boolean {
        return currentVehicleTypeSpots[vehicleType]!! < vehicleSpotsLimit[vehicleType]!!
    }

    private fun getAndReserveFreeSpotFor(vehicle: Vehicle): Long {
        val nextSpot = getNextSpot(vehicle.getType())
        if (nextSpot != -1L) {
            reserveSpot(nextSpot, vehicle)
            currentVehicleTypeSpots[vehicle.getType()] = currentVehicleTypeSpots[vehicle.getType()]!! + 1

            return nextSpot
        }
        return -1
    }

    fun getTicket(vehicle: Vehicle): Ticket? {
        if (isSpotAvailableFor(vehicle.getType())) {
            val spotNumber = getAndReserveFreeSpotFor(vehicle)
            if (spotNumber != -1L) {
                ticketCounter += 1
                return Ticket(ticketCounter, spotNumber, LocalDateTime.now())
            }
        }
        return null
    }

    private fun isSpotTakenByVehicle(spotNumber: Long, vehicle: Vehicle): Boolean {
        return currentSpots[spotNumber]!!.isTaken && currentSpots[spotNumber]!!.vehicle == vehicle
    }
    private fun unreserveSpot(spot: Long): Boolean {
        currentSpots[spot]!!.removeVehicleFromSpot()
        return true
    }

    fun getAndUnreserveSpotFor(vehicle: Vehicle): Boolean{
        if(unreserveSpot(vehicle.getTicket().getSpotNumber())){
            currentVehicleTypeSpots[vehicle.getType()] = currentVehicleTypeSpots[vehicle.getType()]!! - 1
            return true
        }
        return false
    }

    fun getReceipt(vehicle: Vehicle): Receipt? {
        if (isSpotTakenByVehicle(vehicle.getTicket().getSpotNumber(), vehicle)) {
            if (getAndUnreserveSpotFor(vehicle)) {
                val fees = getFeeToPay(vehicle)
                receiptCounter += 1
                return Receipt(receiptCounter, vehicle.getTicket().getSpotNumber(), fees = fees)
            }
        }
        return null
    }

    abstract fun getFeeToPay(vehicle: Vehicle): Long
}


