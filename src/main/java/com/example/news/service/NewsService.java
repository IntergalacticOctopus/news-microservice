package com.example.news.service;

import com.example.news.dto.NewNewsDto;
import com.example.news.dto.NewsDto;
import com.example.news.dto.NewsParamDto;
import com.example.news.dto.UpdateNewsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface NewsService {
    List<NewsDto> getNews(PageRequest pageRequest, NewsParamDto newsParamDto);

    NewsDto getNewsById(Integer id);

    NewsDto addNews(NewNewsDto newNewsDto);

    NewsDto updateNews(Integer newsId, UpdateNewsDto updateNewsDto);

    void deleteNews(Integer newsId) throws JsonProcessingException;
}
