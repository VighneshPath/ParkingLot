package models

import java.time.LocalDateTime

data class Receipt(
    val receiptNumber: Number,
    val spotNumber: Long,
    val entryDateTime: LocalDateTime = LocalDateTime.now(),
    val exitDateTime: LocalDateTime = LocalDateTime.now(),
    val fees: Long
)
