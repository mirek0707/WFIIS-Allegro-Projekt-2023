package pl.flashgoal.news.services;


import pl.flashgoal.news.models.News;
import pl.flashgoal.news.models.NewsDto;
import pl.flashgoal.news.models.NewsDtoEdit;

import java.util.List;

public interface NewsService {

    List<News> getAllNews();

    List<News> getNewsByTag(String tag);

    News createNews(News news);

    News editNews(NewsDtoEdit news);

    void deleteNews(String id);

    News createNewsWithTags(NewsDto newsDto);
}