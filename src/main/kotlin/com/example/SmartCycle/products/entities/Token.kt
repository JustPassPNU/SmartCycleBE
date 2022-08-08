package com.example.SmartCycle.products.entities

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "tokens")
class Token(
    @Id
    val id: String,
    @OneToOne
    @JoinColumn(name = "users_id")
    val user: User
)