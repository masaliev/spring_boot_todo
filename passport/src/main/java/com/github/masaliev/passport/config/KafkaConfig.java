package com.github.masaliev.passport.config;

import com.github.masaliev.shared.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    
    @Bean
    public NewTopic sendMailTopic(){
        return new NewTopic(Constants.KAFKA_TOPIC_SEND_MAIL, 1, (short) 1);
    }
    
}
