package com.example.SmartCycle.products.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class LoginDto(
    @field:NotBlank(message = "id가 없습니다.")
    @field:Size(min = 4, max = 12, message = "id는 4~12자 이어야 합니다.")
    val id: String,
    @field:NotBlank(message = "id가 없습니다.")
    @field:Size(min = 4, max = 12, message = "id는 4~12자 이어야 합니다.")
    val pw: String
)