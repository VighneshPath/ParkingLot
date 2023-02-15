package models

import org.junit.jupiter.api.Assertions.assertEquals
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

//    @Test
//    @DisplayName("should park a two wheeler and unpark it to get fee")
//    fun parkAndUnparkTwoWheeler(){
//        val scooter = Vehicle(VehicleType.TWO_WHEELER)
//
//        val ticket = scooter.parkAndGetTicket(airport)
//
//        assertThat(ticket).usingRecursiveComparison()
//            .comparingOnlyFields("quantity", "type", "price", "userName")
//            .isEqualTo(actualOrder)
//
//        assertEquals(ticket, Ticket(1, 1, LocalDateTime.now()))
//    }
}