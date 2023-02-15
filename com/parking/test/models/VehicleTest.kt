package com.parking.test.models

import com.parking.main.models.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class VehicleTest{
    private lateinit var airport: Airport
    private lateinit var mall: Mall
    private lateinit var stadium: Stadium

    @BeforeEach
    @DisplayName("should setup all locations")
    fun setupLocations(){
        val input = mapOf(VehicleType.TWO_WHEELER to 2L, VehicleType.FOUR_WHEELER to 2L, VehicleType.HEAVY_VEHICLE to 2L)

        airport = Airport(input)
        mall = Mall(input)
        stadium = Stadium(input)
    }

    @Test
    @DisplayName("should park a two wheeler and unpark it to get fee")
    fun parkAndUnparkTwoWheeler(){
        val scooter = Vehicle(VehicleType.TWO_WHEELER)

        val ticket = scooter.parkAndGetTicket(airport)
        val receipt = scooter.unparkAndGetReceipt(airport)

        assertThat(ticket).usingRecursiveComparison()
            .comparingOnlyFields("ticketNumber", "spotNumber")
            .isEqualTo(Ticket(1, 1, LocalDateTime.now()))
        assertThat(receipt).usingRecursiveComparison()
            .comparingOnlyFields("receiptNumber", "spotNumber", "fees")
            .isEqualTo(Receipt(1L, 1, fees = 0))
    }

    @Test
    @DisplayName("should park two two-wheelers and unpark 1st and park the 3rd one in 1st spot")
    fun parkAndUnparkMultipleTwoWheeler(){
        val scooter = Vehicle(VehicleType.TWO_WHEELER)
        val motorcycle = Vehicle(VehicleType.TWO_WHEELER)
        val motorcycle1 = Vehicle(VehicleType.TWO_WHEELER)

        val ticket1 = scooter.parkAndGetTicket(airport)
        val ticket2 = motorcycle.parkAndGetTicket(airport)
        val receipt1 = scooter.unparkAndGetReceipt(airport)
        println(receipt1)
        val ticket3 = motorcycle1.parkAndGetTicket(airport)

        assertThat(ticket1).usingRecursiveComparison()
            .comparingOnlyFields("ticketNumber", "spotNumber")
            .isEqualTo(Ticket(1, 1, LocalDateTime.now()))
        assertThat(ticket2).usingRecursiveComparison()
            .comparingOnlyFields("ticketNumber", "spotNumber")
            .isEqualTo(Ticket(2, 2, LocalDateTime.now()))
        assertThat(receipt1).usingRecursiveComparison()
            .comparingOnlyFields("receiptNumber", "spotNumber", "fees")
            .isEqualTo(Receipt(1L, 1, fees = 0))
        assertThat(ticket3).usingRecursiveComparison()
            .comparingOnlyFields("ticketNumber", "spotNumber")
            .isEqualTo(Ticket(3, 1, LocalDateTime.now()))
    }

}