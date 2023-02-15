package models

import java.time.LocalDateTime

data class Ticket(
    var ticketNumber: Long = 1,
    private val spotNumber: Long,
    private val entryDateTime: LocalDateTime = LocalDateTime.now()
) {
    fun getEntryDateTime(): LocalDateTime {
        return entryDateTime
    }

    fun getSpotNumber(): Long {
        return spotNumber
    }
}

