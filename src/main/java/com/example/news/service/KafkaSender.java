package com.example.news.service;

import com.example.news.dto.NewsDeletionEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaSender {
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void deleteNews(NewsDeletionEvent newsDeletionEvent, String topicName) throws JsonProcessingException {
        String newsAsMessage = objectMapper.writeValueAsString(newsDeletionEvent);
        kafkaTemplate.send(topicName, newsAsMessage);

        log.info("News produced {}", newsAsMessage);

    }
}