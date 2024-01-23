package pl.flashgoal.news;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.flashgoal.news.models.News;
import pl.flashgoal.news.models.Tag;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes=News.class)
public class NewsTests
{
    @Test
    public void test_createNewsWithRequiredFields() {
        News news = new News();
        news.setId("1");
        news.setCreated(ZonedDateTime.now());
        news.setTitle("Test Title");
        news.setContent("Test Content");
        news.setAuthor("Test Author");
        Set<Tag> set = new HashSet<>();
        Tag tag1 = new Tag();
        tag1.setName("tag1");
        set.add(tag1);
        news.setTags(set);

        assertEquals("1", news.getId());
        assertNotNull(news.getCreated());
        assertEquals("Test Title", news.getTitle());
        assertEquals("Test Content", news.getContent());
        assertEquals("Test Author", news.getAuthor());
        assertEquals(news.getTags().size(), 1);

        Tag tag2 = new Tag();
        tag2.setName("tag2");
        news.addTag(tag2);
        assertEquals(news.getTags().size(), 2);
    }
}

