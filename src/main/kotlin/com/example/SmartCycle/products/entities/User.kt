package com.example.SmartCycle.products.entities

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
class User(
    @Id
    val id: String,
    val pw: String,
    val name: String,
    val nickName: String,
    val email: String,
    val phone: String,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    var state: STATE = STATE.NO
) {
    enum class STATE {
        NO,
        YES
    }
}

