package com.example.userservice.repos;
import java.util.Optional;

import com.example.userservice.models.EnumRole;
import com.example.userservice.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface RoleRepo extends MongoRepository<Role, String> {
    Optional<Role> findByName(EnumRole name);
}
