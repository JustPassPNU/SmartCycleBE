package com.example.SmartCycle.products.map

import com.example.SmartCycle.products.dto.ResponseMessage
import com.example.SmartCycle.products.s3.S3Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MapController(
    private val s3Service: S3Service
) {

    @GetMapping("map/{mapName}")
    fun provideMap(@PathVariable mapName: String): ResponseMessage {
        val url = s3Service.getPreSignedURL(mapName)
        return ResponseMessage(
            result = true,
            message = "",
            data = url
        )
    }
}