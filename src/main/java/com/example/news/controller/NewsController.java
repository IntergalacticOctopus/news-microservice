package com.example.news.controller;

import com.example.news.model.NewNewsDto;
import com.example.news.model.NewsDto;
import com.example.news.model.UpdateNewsDto;
import com.example.news.service.NewsService;
import com.example.news.dto.ParamsNewsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Новости", description = "Методы для работы с новостями")
public class NewsController implements NewsApi {

    private final NewsService newsService;

    @Override
    @GetMapping
    @Operation(summary = "Информация о новостях по параметрам")
    public ResponseEntity<List<NewsDto>> newsGet(
            @Parameter @RequestParam(value = "page", defaultValue = "1", required = false) Long page,
            @Parameter @RequestParam(value = "size", defaultValue = "10", required = false) Long size,
            @Parameter @RequestParam(value = "theme", required = false) String theme,
            @Parameter @RequestParam(value = "user_id", required = false) Long userId,
            @Parameter @RequestParam(value = "publication_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publicationDate
    ) {
        log.info("Getting news with page = {}, size = {}, theme = {}, user_id = {}, publication_date = {}",
                page, size,
                theme, userId,
                publicationDate);
        ParamsNewsDto paramsNewsDto = new ParamsNewsDto(page, size, theme, userId, publicationDate);
        List<NewsDto> newsDtoList = newsService.getNews(paramsNewsDto);
        log.info("Got news {}", newsDtoList);
        return ResponseEntity.ok(newsDtoList);
    }

    @Override
    @Operation(summary = "Информация о новостях по ID")
    @GetMapping("/{news_id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable Long news_id) {
        log.info("Getting news with id = {}", news_id);
        NewsDto newsDto = newsService.getNewsById(news_id);
        log.info("Got news {}", newsDto);
        return ResponseEntity.ok(newsDto);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated
    @Operation(summary = "Создание новости",
            description = "Тело запроса: title (Integer) – Заголовок новости" +
                    " theme (String) – Категория новости" +
                    " user_id (Integer) – Идентификатор автора" +
                    " publication_date (String) – Дата публикации")
    public ResponseEntity<NewsDto> addNews(@RequestBody @Valid NewNewsDto newNewsDto) {
        log.info("Creating news {}", newNewsDto);
        NewsDto newsDto = newsService.addNews(newNewsDto);
        log.info("News {} created", newsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsDto);
    }

    @Override
    @PatchMapping("/{news_id}")
    @Operation(summary = "Обновление новости",
            description = "news_id (Integer) – Идентификатор новости" +
                    "Тело запроса: title (Integer) – Заголовок новости" +
                    " theme (String) – Категория новости" +
                    " user_id (Integer) – Идентификатор автора новости" +
                    " publication_date (String) – Дата публикации")
    public ResponseEntity<NewsDto> updateNews(@PathVariable Long news_id,
                                              @RequestBody @Valid UpdateNewsDto updateNewsDto) {
        log.info("Updating news {} with id {}", updateNewsDto, news_id);
        NewsDto newsDto = newsService.updateNews(news_id, updateNewsDto);
        log.info("Updated {} news with id {}", newsDto, news_id);
        return ResponseEntity.ok(newsDto);
    }

    @Override
    @Operation(summary = "Удаление новости")
    @DeleteMapping("/{news_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteNews(@PathVariable Long news_id) {
        log.info("Deleting news by id = {}", news_id);
        boolean isRemoved = false;
        try {
            isRemoved = newsService.deleteNews(news_id);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (!isRemoved) {
            log.info("News by id = {} not found", news_id);
            return ResponseEntity.noContent().build();
        }
        log.info("Deleted news by id = {} successfully", news_id);

        return ResponseEntity.ok().build();
    }
}
