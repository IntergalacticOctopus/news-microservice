package com.example.news.service;

import com.example.news.model.NewNewsDto;
import com.example.news.model.NewsDto;
import com.example.news.model.UpdateNewsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.news.dto.ParamsNewsDto;

import java.util.List;

public interface NewsService {
    List<NewsDto> getNews(ParamsNewsDto paramsNewsDto);

    NewsDto getNewsById(Integer id);

    NewsDto addNews(NewNewsDto newNewsDto);

    NewsDto updateNews(Integer newsId, UpdateNewsDto updateNewsDto);

    boolean deleteNews(Integer newsId) throws JsonProcessingException;
}
