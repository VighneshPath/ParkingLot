import com.parking.main.models.Airport
import com.parking.main.models.Vehicle
import com.parking.main.models.VehicleType

fun main(){
    // Input - two-wheeler -> 2 spots
    // park motorcycle, park scooter, park scooter, upark motorcycle, park motorcycle, unpark scooter
    val input = mapOf(VehicleType.TWO_WHEELER to 2L, VehicleType.FOUR_WHEELER to 0L, VehicleType.HEAVY_VEHICLE to 0L)
    val airport = Airport(input)

    val scooter = Vehicle(VehicleType.TWO_WHEELER)

    scooter.parkAndGetTicket(airport)

    val receipt = scooter.unparkAndGetReceipt(airport)

    print(receipt!!)

    // Expected Output - Receipt for 1st motorcycle, and 1st scooter
}