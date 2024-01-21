package pl.flashgoal.news.controllers;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.flashgoal.news.models.News;
import pl.flashgoal.news.models.NewsDto;
import pl.flashgoal.news.models.NewsDtoEdit;
import pl.flashgoal.news.models.Tag;
import pl.flashgoal.news.services.NewsService;
import pl.flashgoal.news.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private TagService tagService;

    private final RestTemplate restTemplate = new RestTemplate();

    private HttpStatusCode checkCredentials(String userToken, String url){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", userToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> userResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return userResponse.getStatusCode();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllNews(@RequestHeader("Authorization") String userToken) {
        try{
            HttpStatusCode status = checkCredentials(userToken, "http://user-service:8084/api/test/premiumOrAdmin");
            if(status == HttpStatus.OK){
                return ResponseEntity.ok(newsService.getAllNews());
            }
            return ResponseEntity.notFound().build();
        }catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user token");
        }
    }

    @GetMapping("/{tag}")
    public ResponseEntity<?> getNewsByTag(@PathVariable String tag, @RequestHeader("Authorization") String userToken) {
        try{
            HttpStatusCode status = checkCredentials(userToken, "http://user-service:8084/api/test/premiumOrAdmin");
            if(status == HttpStatus.OK){
                return ResponseEntity.ok(newsService.getNewsByTag(tag));
            }
            return ResponseEntity.notFound().build();
        }catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user token");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNews(@RequestBody NewsDto newsDto, @RequestHeader("Authorization") String userToken) {
        try{
            HttpStatusCode status = checkCredentials(userToken, "http://user-service:8084/api/test/admin");
            if(status == HttpStatus.OK){
                News news = new News();
                news.setTitle(newsDto.getTitle());
                news.setContent(newsDto.getContent());
                news.setAuthor(newsDto.getAuthor());
                news.setCreated(ZonedDateTime.now());

                for (String tagName : newsDto.getTags()) {
                    Tag tag = tagService.getOrCreateTag(tagName);
                    news.addTag(tag);
                }

                return ResponseEntity.ok(newsService.createNews(news));
            }
            return ResponseEntity.notFound().build();
        }catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user token");
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editNews(@RequestBody NewsDtoEdit news, @RequestHeader("Authorization") String userToken) {
        try{
            HttpStatusCode status = checkCredentials(userToken, "http://user-service:8084/api/test/premiumOrAdmin");
            if(status == HttpStatus.OK){
                return ResponseEntity.ok(newsService.editNews(news));
            }
            return ResponseEntity.notFound().build();
        }catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user token");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable String id, @RequestHeader("Authorization") String userToken) {
        try{
            HttpStatusCode status = checkCredentials(userToken, "http://user-service:8084/api/test/premiumOrAdmin");
            if(status == HttpStatus.OK){
                newsService.deleteNews(id);
                return ResponseEntity.ok("News deleted successfully");
            }
            return ResponseEntity.notFound().build();
        }catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user token");
        }
    }
}