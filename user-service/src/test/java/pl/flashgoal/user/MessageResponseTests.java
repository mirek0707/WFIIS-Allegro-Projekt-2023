package pl.flashgoal.user;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import pl.flashgoal.users.payload.response.MessageResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = MessageResponse.class)
public class MessageResponseTests {
    @Test
    public void test_createMessageResponse() {
        MessageResponse res = new MessageResponse("test");
        assertEquals("test", res.getMessage());

        res.setMessage("test2");
        assertEquals("test2", res.getMessage());
    }
}
