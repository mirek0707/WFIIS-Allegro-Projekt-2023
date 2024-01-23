package pl.flashgoal.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.flashgoal.users.models.Role;
import pl.flashgoal.users.models.EnumRole;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Role.class)
public class RoleTests
{
    @Test
    public void test_createRoleByParameterizedConstructor() {
        Role role = new Role(EnumRole.ROLE_ADMIN);
        role.setId("1");

        assertEquals("1", role.getId());
        assertEquals(EnumRole.ROLE_ADMIN, role.getName());
    }
    @Test
    public void test_createRoleByDefaultConstructor() {
        Role role = new Role();
        role.setId("1");
        role.setName(EnumRole.ROLE_USER);

        assertEquals("1", role.getId());
        assertEquals(EnumRole.ROLE_USER, role.getName());
    }
}
