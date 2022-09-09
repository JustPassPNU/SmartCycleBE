package com.example.SmartCycle.products.token

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.example.SmartCycle.products.entities.Token

@Repository
interface TokenRepository: JpaRepository<Token, String> {

    fun findFirstById(id: String): Token?

}