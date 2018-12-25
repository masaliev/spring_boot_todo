package com.github.masaliev.passport.service;

import com.github.masaliev.shared.Constants;
import com.github.masaliev.shared.SendMailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MailSender {

    @Autowired
    private KafkaTemplate<String, SendMailRequest> kafkaTemplate;

    public void sendMail(String email, String subject, String message) {
        SendMailRequest sendMailRequest = new SendMailRequest(email, subject, message);
        kafkaTemplate.send(Constants.KAFKA_TOPIC_SEND_MAIL, sendMailRequest);
    }
}
