package pl.flashgoal.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pl.flashgoal.users.models.EnumRole;
import pl.flashgoal.users.models.Role;
import pl.flashgoal.users.models.User;
import pl.flashgoal.users.security.services.UserDetailsImpl;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = UserDetailsImpl.class)

public class UserDetailsImplTests {
    @Test
    public void test_createRoleByParameterizedConstructor() {
        Set<String> tags = new HashSet<>();
        tags.add("test tag");

        User user = new User("test name", "test@email.com", "testPwd", tags);
        UserDetailsImpl userDetailsImpl2 = UserDetailsImpl.build(user);
        Set<Role> set = new HashSet<>();
        Role role = new Role();
        role.setName(EnumRole.ROLE_USER);
        set.add(role);
        user.setRoles(set);
        user.setId("1");

        UserDetailsImpl userDetailsImpl = UserDetailsImpl.build(user);

        assertEquals("1", userDetailsImpl.getId());
        assertEquals("test@email.com", userDetailsImpl.getEmail());
        assertEquals("testPwd", userDetailsImpl.getPassword());
        assertEquals("test name", userDetailsImpl.getUsername());
        assertTrue(userDetailsImpl.isAccountNonExpired());
        assertTrue(userDetailsImpl.isAccountNonLocked());
        assertTrue(userDetailsImpl.isCredentialsNonExpired());
        assertTrue(userDetailsImpl.isEnabled());
        assertNotNull(userDetailsImpl.getAuthorities());
        assertNotEquals(userDetailsImpl, userDetailsImpl2);
    }
}
