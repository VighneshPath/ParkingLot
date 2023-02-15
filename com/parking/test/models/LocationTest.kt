package com.parking.test.models


import com.parking.main.models.Receipt
import com.parking.main.models.Ticket
import com.parking.main.models.Vehicle
import com.parking.main.models.VehicleType
import com.parking.main.models.locations.Airport
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class LocationTest{
    private lateinit var scooter: Vehicle
    private lateinit var car: Vehicle
    private lateinit var truck: Vehicle

    @BeforeEach
    @DisplayName("should setup all locations")
    fun setupLocations(){
        scooter = Vehicle(VehicleType.TWO_WHEELER)
        car = Vehicle(VehicleType.FOUR_WHEELER)
        truck = Vehicle(VehicleType.HEAVY_VEHICLE)
    }


    @Test
    @DisplayName("should get a ticket in an airport with space for two wheeler")
    fun shouldGetTicket(){
        val spotInput = mapOf(VehicleType.TWO_WHEELER to 2L, VehicleType.FOUR_WHEELER to 2L, VehicleType.HEAVY_VEHICLE to 2L)
        val airport = Airport(spotInput)

        val ticket = airport.getTicket(scooter)

        Assertions.assertThat(ticket).usingRecursiveComparison()
            .comparingOnlyFields("ticketNumber", "spotNumber")
            .isEqualTo(Ticket(1, 1, LocalDateTime.now()))
    }

    @Test
    @DisplayName("should get a receipt in a stadium with space for two wheeler")
    fun shouldGetReceipt(){
        val spotInput = mapOf(VehicleType.TWO_WHEELER to 2L, VehicleType.FOUR_WHEELER to 2L, VehicleType.HEAVY_VEHICLE to 2L)
        val airport = Airport(spotInput)

        scooter.parkAndGetTicket(airport)
        val receipt = airport.getReceipt(scooter)

        Assertions.assertThat(receipt).usingRecursiveComparison()
            .comparingOnlyFields("ticketNumber", "spotNumber")
            .isEqualTo(Receipt(1, 1, fees = 0))
    }

}