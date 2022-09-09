package com.example.SmartCycle.products.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.example.SmartCycle.products.entities.User

@Repository
interface UserRepository: JpaRepository<User, Long> {

    fun findFirstById(id: String): User?

}