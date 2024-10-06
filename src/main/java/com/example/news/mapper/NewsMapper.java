package com.example.news.mapper;

import com.example.news.model.NewNewsDto;
import com.example.news.model.NewsDto;
import com.example.news.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface NewsMapper {
    @Mapping(target = "userId", source = "newsId") // Отобразим newsId из News в userId в NewsDto
    NewsDto toNewsDto(News news);

    @Mapping(target = "newsId", source = "userId") // Отобразим userId из NewNewsDto в newsId в News
    News toNews(NewNewsDto newNewsDto);
}
