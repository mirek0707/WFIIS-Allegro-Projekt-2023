package pl.flashgoal.news.controllers;

import pl.flashgoal.news.models.News;
import pl.flashgoal.news.models.NewsDto;
import pl.flashgoal.news.models.NewsDtoEdit;
import pl.flashgoal.news.models.Tag;
import pl.flashgoal.news.services.NewsService;
import pl.flashgoal.news.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private TagService tagService;
    @GetMapping("/all")
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    @GetMapping("/{tag}")
    public List<News> getNewsByTag(@PathVariable String tag) {
        return newsService.getNewsByTag(tag);
    }

    @PostMapping("/create")
    public News createNews(@RequestBody NewsDto newsDto) {
        // Przyjmujemy NewsDto, które zawiera informacje o artykule i tagach
        News news = new News();
        news.setTitle(newsDto.getTitle());
        news.setContent(newsDto.getContent());
        news.setAuthor(newsDto.getAuthor());
        news.setCreated(ZonedDateTime.now());

        // Dodajemy tagi do artykułu
        for (String tagName : newsDto.getTags()) {
            Tag tag = tagService.getOrCreateTag(tagName); // Metoda do pobierania lub tworzenia tagu w serwisie TagService
            news.addTag(tag);
        }

        // Zapisujemy artykuł z tagami
        return newsService.createNews(news);
    }

    @PutMapping("/edit")
    public News editNews(@RequestBody NewsDtoEdit news) {
        return newsService.editNews(news);
    }

    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable String id) {
        newsService.deleteNews(id);
    }
}