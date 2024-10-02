package com.example.news.service;

import com.example.news.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface NewsService {
    List<NewsDto> getNews(ParamsNewsDto paramsNewsDto);

    NewsDto getNewsById(Integer id);

    NewsDto addNews(NewNewsDto newNewsDto);

    NewsDto updateNews(Integer newsId, UpdateNewsDto updateNewsDto);

    boolean deleteNews(Integer newsId) throws JsonProcessingException;
}
