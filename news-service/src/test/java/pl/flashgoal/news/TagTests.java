package pl.flashgoal.news;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.flashgoal.news.models.Tag;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = Tag.class)
public class TagTests
{
    @Test
    public void test_createtagDtoWithRequiredFields() {
        Tag tag = new Tag();
        tag.setName("name");
        tag.setId("1");


        assertEquals("name", tag.getName());
        assertEquals("1", tag.getId());
    }
}

