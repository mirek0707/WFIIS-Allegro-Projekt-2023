package pl.flashgoal.news.services;


import pl.flashgoal.news.models.News;
import pl.flashgoal.news.models.NewsDto;
import pl.flashgoal.news.models.NewsDtoEdit;
import pl.flashgoal.news.models.Tag;
import pl.flashgoal.news.repos.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepo  newsRepo;

    @Autowired
    private TagService  tagService;
    @Override
    public List<News> getAllNews() {
        return newsRepo.findAll();
    }

    @Override
    public List<News> getNewsByTag(String tag) {
        return newsRepo.findByTagsName(tag);
    }

    @Override
    public News createNews(News news) {
        return newsRepo.save(news);
    }

    @Override
    public News editNews(NewsDtoEdit news) {
        Optional<News> existingNews = newsRepo.findById(news.getId());

        if (existingNews.isPresent()) {
            News updatedNews = existingNews.get();
            updatedNews.setTitle(news.getTitle());
            updatedNews.setContent(news.getContent());
            updatedNews.setAuthor(news.getAuthor());

            updatedNews.setTags(new HashSet<>());

            for (String tagName : news.getTags()) {
                Tag tag = tagService.getOrCreateTag(tagName);
                updatedNews.addTag(tag);
            }

            return newsRepo.save(updatedNews);
        } else {
            throw new RuntimeException("News not found with id: " + news.getId());
        }
    }

    @Override
    public void deleteNews(String id) {
        newsRepo.deleteById(id);
    }

    @Override
    public News createNewsWithTags(NewsDto newsDto) {
        // Implementacja tworzenia artykułów z tagami
        // Możesz dostosować tę logikę w zależności od swoich potrzeb
        News news = new News();
        news.setTitle(newsDto.getTitle());
        news.setContent(newsDto.getContent());
        news.setAuthor(newsDto.getAuthor());

        for (String tagName : newsDto.getTags()) {
            // Tutaj możesz dodać logikę do pobierania lub tworzenia tagu
            // Na razie zakładam, że już masz zaimplementowany serwis do obsługi Tagów
            Tag tag = tagService.getOrCreateTag(tagName);
            news.addTag(tag);
        }

        return newsRepo.save(news);
    }
}