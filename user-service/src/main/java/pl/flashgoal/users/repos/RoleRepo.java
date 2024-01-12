package pl.flashgoal.users.repos;
import java.util.Optional;

import pl.flashgoal.users.models.EnumRole;
import pl.flashgoal.users.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface RoleRepo extends MongoRepository<Role, String> {
    Optional<Role> findByName(EnumRole name);
}
