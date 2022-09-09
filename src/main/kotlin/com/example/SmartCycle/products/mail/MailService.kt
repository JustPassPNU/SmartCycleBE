package com.example.SmartCycle.products.mail

import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import javax.mail.Message
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Service
class MailService(
    private val javaMailSender: JavaMailSender
) {
    @Throws(Exception::class)
    private fun createMessage(code: String, email: String): MimeMessage {
        val message: MimeMessage = javaMailSender.createMimeMessage()
        message.addRecipients(Message.RecipientType.TO, email)
        message.subject = "SmartCycle 인증 코드입니다."
        message.setText("인증 코드:\n$code")
        message.setFrom(InternetAddress("swk4919@naver.com"))
        return message
    }

    @Throws(Exception::class)
    fun sendMail(code: String, email: String) {
        try {
            val mimeMessage = createMessage(code, email)
            javaMailSender.send(mimeMessage)
        } catch (mailException: MailException) {
            mailException.printStackTrace()
            throw IllegalAccessException()
        }
    }
}