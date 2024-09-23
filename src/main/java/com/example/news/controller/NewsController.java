package com.example.news.controller;

import com.example.news.dto.NewNewsDto;
import com.example.news.dto.NewsDto;
import com.example.news.dto.NewsParamDto;
import com.example.news.dto.UpdateNewsDto;
import com.example.news.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public List<NewsDto> getNews(@Parameter(description = "Номер страницы для пагинации") @RequestParam(required = false, defaultValue = "1") Integer page,
                                 @Parameter(description = "Количество записей на странице") @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @Parameter(description = "Значение для фильтрация по теме") @RequestParam(required = false) String theme,
                                 @Parameter(description = "Значение для фильтрация по ID автора") @RequestParam(value = "user_id", required = false) @PositiveOrZero Integer userId,
                                 @Parameter(description = "Для фильтрация по дате публикации") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate publication_date) {
        final PageRequest pageRequest = PageRequest.of(page - 1, size);
        final NewsParamDto newsParamDto = new NewsParamDto(theme, userId, publication_date);
        log.info("Getting news with page = {}, size = {}, theme = {}, user_id = {}, publication_date = {}", page, size, theme, userId, publication_date);
        List<NewsDto> newsDtoList = newsService.getNews(pageRequest, newsParamDto);
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
    public void deleteNews(@Parameter(description = "ID новости") @PathVariable Integer news_id) {
        log.info("Deleting news with id {}", news_id);
        newsService.deleteNews(news_id);
        log.info("Deleted news with id {}", news_id);
    }
}
