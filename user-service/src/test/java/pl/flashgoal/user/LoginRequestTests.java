package pl.flashgoal.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.flashgoal.users.payload.request.LoginRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = LoginRequest.class)
public class LoginRequestTests
{
    @Test
    public void test_createLoginRequestByDefaultConstructor() {
        LoginRequest req = new LoginRequest();
        req.setUsername("test username");
        req.setPassword("testPwd");

        assertEquals("test username", req.getUsername());
        assertEquals("testPwd", req.getPassword());
    }
}
