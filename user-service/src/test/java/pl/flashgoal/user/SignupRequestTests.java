package pl.flashgoal.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.flashgoal.users.payload.request.SignupRequest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SignupRequest.class)
public class SignupRequestTests {
    @Test
    public void test_createSignupRequestByDefaultConstructor() {
        SignupRequest req = new SignupRequest();
        req.setUsername("test username");
        req.setEmail("test@email.com");
        req.setPassword("testPwd");

        Set<String> set = new HashSet<>();
        set.add("user");
        req.setRole(set);

        assertEquals("test username", req.getUsername());
        assertEquals("test@email.com", req.getEmail());
        assertEquals("testPwd", req.getPassword());
        assertEquals(1, req.getRoles().size());
    }
}
