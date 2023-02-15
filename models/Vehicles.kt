package models

import java.time.LocalDateTime

class Vehicle(private val type: VehicleType) {
    private var isParked: Boolean = false
    private lateinit var ticket: Ticket

    fun parkAndGetTicket(location: Location): Ticket? {
        if (!isParked) {
            val ticket = location.getTicket(this)
            this.ticket = ticket!!
            isParked = true
            return ticket
        }
        return null
    }

    fun unparkAndGetReceipt(location: Location): Receipt? {
        if (isParked) {
            val receipt = location.getReceipt(this)
            isParked = false
            return receipt
        }
        // Change to exception later on
        return null
    }

    fun getTicket(): Ticket {
        return ticket.copy()
    }

    fun getType(): VehicleType {
        return type
    }


    fun getTicketStartTime(): LocalDateTime {
        return ticket.getEntryDateTime()
    }
}

// Fee Model is always same for any location, This can maybe be an enum?