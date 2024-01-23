package pl.flashgoal.user;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import pl.flashgoal.users.payload.response.JwtResponse;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ContextConfiguration(classes = JwtResponse.class)
public class JwtResponseTests {
    @Test
    public void test_createUserByDefaultConstructor() {
        List<String> list = new ArrayList<>();
        list.add("user");
        JwtResponse res = new JwtResponse("token", "1", "test username", "test@email.com", list);

        assertEquals("token", res.getAccessToken());
        assertEquals("Bearer", res.getTokenType());
        assertEquals("1", res.getId());
        assertEquals("test username", res.getUsername());
        assertEquals("test@email.com", res.getEmail());
        assertEquals(1, res.getRoles().size());

        res.setId("2");
        res.setAccessToken("token2");
        res.setTokenType("Type");
        res.setEmail("test2@email.com");
        res.setUsername("test");

        assertEquals("token2", res.getAccessToken());
        assertEquals("Type", res.getTokenType());
        assertEquals("2", res.getId());
        assertEquals("test", res.getUsername());
        assertEquals("test2@email.com", res.getEmail());
    }
}
