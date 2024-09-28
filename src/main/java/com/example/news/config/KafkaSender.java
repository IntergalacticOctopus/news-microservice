package com.example.news.config;

import com.example.news.dto.NewsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaSender {

    @Value("${topic.name}")
    private String newsTopic;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public String sendMessage(NewsDto newsDto) throws JsonProcessingException {
        String newsAsMessage = objectMapper.writeValueAsString(newsDto);
        kafkaTemplate.send(newsTopic, newsAsMessage);

        log.info("News produced {}", newsAsMessage);

        return "message sent";
    }
}