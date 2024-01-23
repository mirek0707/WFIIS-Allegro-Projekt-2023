package pl.flashgoal.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.flashgoal.users.models.User;
import pl.flashgoal.users.models.Role;
import pl.flashgoal.users.models.EnumRole;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = User.class)
public class UserTests
{
    @Test
    public void test_createUserByParameterizedConstructor() {
        Set<String> tags = new HashSet<>();
        tags.add("test tag");

        User user = new User("test username", "test@email.com", "testPwd", tags);

        assertEquals("test username", user.getUsername());
        assertEquals("test@email.com", user.getEmail());
        assertEquals("testPwd", user.getPassword());
        assertEquals(1, user.getFavouriteTags().size());
    }
    @Test
    public void test_createUserByDefaultConstructor() {
        User user = new User();
        user.setId("1");
        user.setUsername("test username");
        user.setEmail("test@email.com");
        user.setPassword("testPwd");

        Set<Role> set = new HashSet<>();
        Role role = new Role();
        role.setName(EnumRole.ROLE_USER);
        set.add(role);
        user.setRoles(set);

        Set<String> tags = new HashSet<>();
        tags.add("test tag");
        user.setFavouriteTags(tags);

        assertEquals("1", user.getId());
        assertEquals("test username", user.getUsername());
        assertEquals("test@email.com", user.getEmail());
        assertEquals("testPwd", user.getPassword());
        assertEquals(1, user.getRoles().size());
        assertEquals(1, user.getFavouriteTags().size());
    }
}
