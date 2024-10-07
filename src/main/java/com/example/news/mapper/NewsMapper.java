package com.example.news.mapper;

import com.example.news.model.NewNewsDto;
import com.example.news.model.NewsDto;
import com.example.news.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface NewsMapper {
    @Mapping(target = "userId", source = "newsId")
    NewsDto toNewsDto(News news);

    @Mapping(target = "newsId", source = "userId")
    News toNews(NewNewsDto newNewsDto);
}
