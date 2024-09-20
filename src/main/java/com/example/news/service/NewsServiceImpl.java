package com.example.news.service;

import com.example.news.dto.NewNewsDto;
import com.example.news.dto.NewsDto;
import com.example.news.dto.NewsParamDto;
import com.example.news.exseption.errors.NotFoundException;
import com.example.news.mapper.NewsMapper;
import com.example.news.model.News;
import com.example.news.repository.NewsRepository;
import com.example.themes.model.Theme;
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
    public List<NewsDto> getNews (PageRequest pageRequest, NewsParamDto newsParamDto) {
        String theme = newsParamDto.getTheme();
        Integer userId = newsParamDto.getUserId();
        LocalDate publicationDate = newsParamDto.getPublicationDate();
        List<News> newsList = newsRepository.getNewsByParams(theme, userId, publicationDate, pageRequest);
        List<NewsDto> newsDtoList = newsList.stream()
                .map(news -> newsMapper.toNewsDto(news))
                .collect(Collectors.toList());
        return newsDtoList;
    }
    public NewsDto getNewsById (Integer id) {
        final News news = newsRepository.getByNewsId(id)
                .orElseThrow(
                        () -> new NotFoundException("Новость не найдена или уже удалена")
                );
        NewsDto newsDto = newsMapper.toNewsDto(news);
        return newsDto;
    }
    public NewsDto addNews(NewNewsDto newNewsDto) {
        Theme theme = new Theme (newNewsDto.getTheme());
        News news = newsMapper.toNews(newNewsDto, theme);
        final News newsFromBd = newsRepository.save(news);
        NewsDto newsDto = newsMapper.toNewsDto(newsFromBd);
        return newsDto;
    }
    public NewsDto updateNews(Integer newsId, NewNewsDto newNewsDto) {
        News newsFromDb = newsRepository.findById(newsId)
                .orElseThrow(
                        () -> new NotFoundException("Новость не найдена")
                );
        if (newNewsDto.getTitle() != null) {
            newsFromDb.setTitle(newNewsDto.getTitle());
        }
        if (newNewsDto.getUserId() != null) {
            newsFromDb.setUserId(newNewsDto.getUserId());
        }
        if (newNewsDto.getTheme() != null) {
            Theme existingTheme = newsFromDb.getTheme();
            existingTheme.setThemeName(newNewsDto.getTheme());
        }
        if (newNewsDto.getPublicationDate() != null) {
            newsFromDb.setPublicationDate(newNewsDto.getPublicationDate());
        }
        NewsDto newsDto = newsMapper.toNewsDto(newsRepository.save(newsFromDb));
        return newsDto;
    }
    public void deleteNews(Integer news_id) {
        newsRepository.deleteById(news_id);
    }

}
