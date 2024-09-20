package com.example.news.controller;

import com.example.news.dto.NewNewsDto;
import com.example.news.dto.NewsDto;
import com.example.news.dto.NewsParamDto;
import com.example.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@Slf4j
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public List<NewsDto> getNews(@RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false) String theme,
                                 @RequestParam(required = false) @PositiveOrZero Integer user_id,
                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate publication_date) {
        final PageRequest pageRequest = PageRequest.of(page - 1, size);
        final NewsParamDto newsParamDto = new NewsParamDto(theme, user_id, publication_date);
        log.info("Getting news with page = {}, size = {}, theme = {}, user_id = {}, publication_date = {}", page, size, theme, user_id, publication_date);
        List<NewsDto> newsDtoList = newsService.getNews(pageRequest, newsParamDto);
        log.info("Got news {}", newsDtoList);
        return newsDtoList;
    }

    @GetMapping("/{news_id}")
    public NewsDto getNewsById(@PathVariable Integer news_id) {
        log.info("Getting news with id = {}", news_id);
        NewsDto newsDto = newsService.getNewsById(news_id);
        log.info("Got news {}", newsDto);
        return newsDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDto addNews(@RequestBody @Valid NewNewsDto newNewsDto) {
        log.info("Creating news {}", newNewsDto);
        NewsDto newsDto = newsService.addNews(newNewsDto);
        log.info("News {} created", newsDto);
        return newsDto;
    }

    @PatchMapping
    public NewsDto updateNews(@PathVariable Integer news_id,
                              @RequestParam @Valid NewNewsDto newNewsDto) {
        log.info("Updating news {} with id {}", newNewsDto, news_id);
        NewsDto newsDto = newsService.updateNews(news_id, newNewsDto);
        log.info("Updated {} news with id {}", newsDto, news_id);
        return newsDto;
    }

    @DeleteMapping("/{news_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(@PathVariable Integer news_id) {
        log.info("Deleting news with id {}", news_id);
        newsService.deleteNews(news_id);
        log.info("Deleted news with id {}", news_id);
    }
}
