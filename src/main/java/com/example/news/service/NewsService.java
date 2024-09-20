package com.example.news.service;

import com.example.news.dto.NewNewsDto;
import com.example.news.dto.NewsDto;
import com.example.news.dto.NewsParamDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface NewsService {
    List<NewsDto> getNews (PageRequest pageRequest, NewsParamDto newsParamDto);

    NewsDto getNewsById(Integer id);
    NewsDto addNews(NewNewsDto newNewsDto);

    NewsDto updateNews(Integer newsId, NewNewsDto newNewsDto);

    void deleteNews(Integer newsId);
}
