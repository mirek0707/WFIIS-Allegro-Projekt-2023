package pl.flashgoal.news;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.flashgoal.news.models.News;
import pl.flashgoal.news.models.NewsDto;
import pl.flashgoal.news.models.Tag;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes = NewsDto.class)
public class NewsDtoTests
{
    @Test
    public void test_createNewsDtoWithRequiredFields() {
        NewsDto news = new NewsDto();
        news.setTitle("Test Title");
        news.setContent("Test Content");
        news.setAuthor("Test Author");
        List<String> list = new ArrayList<>();
        list.add("tag1");
        news.setTags(list);


        assertEquals("Test Title", news.getTitle());
        assertEquals("Test Content", news.getContent());
        assertEquals("Test Author", news.getAuthor());
        assertEquals(news.getTags().size(), 1);
    }
}

