package com.example.SmartCycle.products.mail

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

@Configuration
class MailConfig {

    @Value("\${naver.mail.username}")
    private lateinit var username: String

    @Value("\${naver.mail.password}")
    private lateinit var password: String

    @Bean
    fun javaMailService(): JavaMailSender {
        val javaMailSender = JavaMailSenderImpl().apply {
            host = "smtp.naver.com"
            username = this@MailConfig.username
            password = this@MailConfig.password
            port = 465
            javaMailProperties = mailProperties
            protocol = "smtps"
        }
        return javaMailSender
    }

    private val mailProperties: Properties
        private get() {
            val properties = Properties()
            properties.setProperty("mail.smtp.auth", "true")
            properties.setProperty("mail.smtp.starttls.enable", "true")
            properties.setProperty("mail.debug", "true")
            properties.setProperty("mail.smtps.ssl.trust", "*")
            properties.setProperty("mail.smtps.ssl.checkserveridentity", "true")
            return properties
        }
}