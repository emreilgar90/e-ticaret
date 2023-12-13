package org.example.model
import java.time.LocalDateTime

data class BaseEntity(
        val createdDate: LocalDateTime?=null,
        val updateDate: LocalDateTime?=null
)
