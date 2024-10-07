package com.example.news.service;

import com.example.news.controller.exseption.NotFoundException;
import com.example.news.dto.NewsDeletionEvent;
import com.example.news.mapper.NewsMapper;
import com.example.news.model.*;
import com.example.news.repository.NewsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.news.dto.ParamsNewsDto;
import com.example.news.model.News;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final KafkaSender kafkaSender;
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    private static final String NEWS_NOT_FOUND = "Новость не найдена или уже удалена";

    public List<NewsDto> getNews(ParamsNewsDto paramsNewsDto) {
        PageRequest pageRequest = PageRequest.of(paramsNewsDto.getPage() - 1, paramsNewsDto.getSize());
        List<News> newsList = newsRepository.getNewsByParams(paramsNewsDto.getTheme(),
                paramsNewsDto.getUser_id(),
                paramsNewsDto.getPublication_date(),
                pageRequest);
        if (newsList.isEmpty()) {
            throw new NotFoundException(NEWS_NOT_FOUND);
        }
        return newsList.stream()
                .map(newsMapper::toNewsDto)
                .collect(Collectors.toList());
    }

    public NewsDto getNewsById(Long id) {
        final News news = newsRepository.getByNewsId(id)
                .orElseThrow(
                        () -> new NotFoundException(NEWS_NOT_FOUND)
                );
        return newsMapper.toNewsDto(news);
    }

    public NewsDto addNews(NewNewsDto newNewsDto) {
        News news = newsMapper.toNews(newNewsDto);
        return newsMapper.toNewsDto(newsRepository.save(news));
    }

    public NewsDto updateNews(Long newsId, UpdateNewsDto updateNewsDto) {
        News newsFromDb = newsRepository.findById(newsId)
                .orElseThrow(() -> new NotFoundException(NEWS_NOT_FOUND));

        Optional.ofNullable(updateNewsDto.getTitle()).ifPresent(newsFromDb::setTitle);
        Optional.ofNullable(updateNewsDto.getUserId()).ifPresent(newsFromDb::setUserId);
        Optional.ofNullable(updateNewsDto.getTheme()).ifPresent(newsFromDb::setTheme);
        Optional.ofNullable(updateNewsDto.getPublicationDate()).ifPresent(newsFromDb::setPublicationDate);

        return newsMapper.toNewsDto(newsRepository.save(newsFromDb));
    }

    public boolean deleteNews(Long news_id) throws JsonProcessingException {
        try {
            newsRepository.findById(news_id).orElseThrow(() -> new NotFoundException("News " + news_id + " not found"));
            newsRepository.deleteById(news_id);
            kafkaSender.deleteNews(new NewsDeletionEvent(news_id), "news-deletion-topic");
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

}
