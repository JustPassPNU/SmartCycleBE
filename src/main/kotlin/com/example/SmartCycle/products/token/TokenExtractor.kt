package com.example.SmartCycle.products.token

import javax.servlet.http.HttpServletRequest

class TokenExtractor {

    companion object{
        const val AUTHORIZATION_HEADER_PREFIX = "Authorization"
        const val BEARER_TYPE_PREFIX = "Bearer "

        fun extract(request: HttpServletRequest): String? {
            val authorization: String? = request.getHeader(AUTHORIZATION_HEADER_PREFIX)
            authorization?.let {
                if(authorization.startsWith(BEARER_TYPE_PREFIX)) {
                    return authorization.substring(BEARER_TYPE_PREFIX.length)
                }
            }
            return null
        }
    }
}