package pl.flashgoal.news;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.flashgoal.news.models.NewsDto;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


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

