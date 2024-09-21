package com.example.news.service;

import com.example.news.dto.NewNewsDto;
import com.example.news.dto.NewsDto;
import com.example.news.dto.NewsParamDto;
import com.example.news.dto.UpdateNewsDto;
import com.example.news.exseption.errors.NotFoundException;
import com.example.news.mapper.NewsMapper;
import com.example.news.model.News;
import com.example.news.repository.NewsRepository;
import com.example.news.exseption.model.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    public List<NewsDto> getNews(PageRequest pageRequest, NewsParamDto newsParamDto) {
        String theme = newsParamDto.getTheme();
        Integer userId = newsParamDto.getUserId();
        LocalDate publicationDate = newsParamDto.getPublicationDate();
        List<News> newsList = newsRepository.getNewsByParams(theme, userId, publicationDate, pageRequest);
        if (newsList.isEmpty()) {
            throw new NotFoundException("Новостей по данному запросу не найдено");
        }
        return newsList.stream()
                .map(news -> newsMapper.toNewsDto(news))
                .collect(Collectors.toList());
    }

    public NewsDto getNewsById(Integer id) {
        final News news = newsRepository.getByNewsId(id)
                .orElseThrow(
                        () -> new NotFoundException("Новость не найдена или уже удалена")
                );
        return newsMapper.toNewsDto(news);
    }

    public NewsDto addNews(NewNewsDto newNewsDto) {
        Theme theme = new Theme(newNewsDto.getTheme());
        News news = newsMapper.toNews(newNewsDto, theme);
        final News newsFromBd = newsRepository.save(news);
        return newsMapper.toNewsDto(newsFromBd);
    }

    public NewsDto updateNews(Integer newsId, UpdateNewsDto updateNewsDto) {
        News newsFromDb = newsRepository.findById(newsId)
                .orElseThrow(
                        () -> new NotFoundException("Новость не найдена")
                );
        if (updateNewsDto.getTitle() != null) {
            newsFromDb.setTitle(updateNewsDto.getTitle());
        }
        if (updateNewsDto.getUserId() != null) {
            newsFromDb.setUserId(updateNewsDto.getUserId());
        }
        if (updateNewsDto.getTheme() != null) {
            Theme existingTheme = newsFromDb.getTheme();
            existingTheme.setThemeName(updateNewsDto.getTheme());
        }
        if (updateNewsDto.getPublicationDate() != null) {
            newsFromDb.setPublicationDate(updateNewsDto.getPublicationDate());
        }
        return newsMapper.toNewsDto(newsRepository.save(newsFromDb));
    }

    public void deleteNews(Integer news_id) {
        newsRepository.deleteById(news_id);
    }

}
