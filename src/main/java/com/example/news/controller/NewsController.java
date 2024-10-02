package com.example.news.controller;

import com.example.news.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.news.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Новости", description = "Методы для работы с новостями")
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    @Operation(summary = "Информация о новостях по параметрам")
    public List<NewsDto> getNews(@Valid @ParameterObject ParamsNewsDto paramsNewsDto) {
        log.info("Getting news with page = {}, size = {}, theme = {}, user_id = {}, publication_date = {}",
                paramsNewsDto.getPage(), paramsNewsDto.getSize(),
                paramsNewsDto.getTheme(), paramsNewsDto.getUser_id(),
                paramsNewsDto.getPublication_date());
        List<NewsDto> newsDtoList = newsService.getNews(paramsNewsDto);
        log.info("Got news {}", newsDtoList);
        return newsDtoList;
    }

    @Operation(summary = "Информация о новостях по ID")
    @GetMapping("/{news_id}")
    public NewsDto getNewsById(@Parameter(description = "Идентификатор новости") @PathVariable @PositiveOrZero Integer news_id) {
        log.info("Getting news with id = {}", news_id);
        NewsDto newsDto = newsService.getNewsById(news_id);
        log.info("Got news {}", newsDto);
        return newsDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated
    @Operation(summary = "Создание новости",
            description = "Тело запроса: title (Integer) – Заголовок новости" +
                    " theme (String) – Категория новости" +
                    " user_id (Integer) – Идентификатор автора" +
                    " publication_date (String) – Дата публикации")
    public NewsDto addNews(@RequestBody @Valid NewNewsDto newNewsDto) {
        log.info("Creating news {}", newNewsDto);
        NewsDto newsDto = newsService.addNews(newNewsDto);
        log.info("News {} created", newsDto);
        return newsDto;
    }

    @PatchMapping("/{news_id}")
    @Operation(summary = "Обновление новости",
            description = "news_id (Integer) – Идентификатор новости" +
                    "Тело запроса: title (Integer) – Заголовок новости" +
                    " theme (String) – Категория новости" +
                    " user_id (Integer) – Идентификатор автора новости" +
                    " publication_date (String) – Дата публикации")
    public NewsDto updateNews(@PathVariable Integer news_id,
                              @RequestBody @Valid UpdateNewsDto updateNewsDto) {
        log.info("Updating news {} with id {}", updateNewsDto, news_id);
        NewsDto newsDto = newsService.updateNews(news_id, updateNewsDto);
        log.info("Updated {} news with id {}", newsDto, news_id);
        return newsDto;
    }


    @Operation(summary = "Удаление новости")
    @DeleteMapping("/{news_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteNews(@Parameter(description = "ID новости") @PathVariable Integer news_id) throws JsonProcessingException {
        log.info("Deleting news by id = {}", news_id);
        boolean isRemoved = newsService.deleteNews(news_id);
        if (!isRemoved) {
            log.info("News by id = {} not found", news_id);
            return ResponseEntity.noContent().build();
        }
        log.info("Deleted news by id = {} successfully", news_id);

        return ResponseEntity.ok().build();
    }
}
