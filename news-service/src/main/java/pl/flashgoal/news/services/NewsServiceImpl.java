package pl.flashgoal.news.services;


import pl.flashgoal.news.models.News;
import pl.flashgoal.news.models.NewsDto;
import pl.flashgoal.news.models.NewsDtoEdit;
import pl.flashgoal.news.models.Tag;
import pl.flashgoal.news.repos.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        List<News> result = newsRepo.findByTagsName(tag);
        List<News> list = new ArrayList<>();

        for (News news: result) {
            List<Map<String, String>> allTagsMap = newsRepo.findTagsForNews(news.getId());
            Set<Tag> set = new HashSet<>();
            for(Map<String, String> tagMap : allTagsMap){
                Tag tagObj = new Tag();
                tagObj.setId(tagMap.get("id"));
                tagObj.setName(tagMap.get("name"));
                set.add(tagObj);
            }
            news.setTags(set);
            list.add(news);
        }
        return list;
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
        News news = new News();
        news.setTitle(newsDto.getTitle());
        news.setContent(newsDto.getContent());
        news.setAuthor(newsDto.getAuthor());

        for (String tagName : newsDto.getTags()) {
            Tag tag = tagService.getOrCreateTag(tagName);
            news.addTag(tag);
        }

        return newsRepo.save(news);
    }
}