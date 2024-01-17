package pl.flashgoal.users.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import pl.flashgoal.users.models.EnumRole;
import pl.flashgoal.users.models.Role;
import pl.flashgoal.users.models.User;
import pl.flashgoal.users.payload.response.MessageResponse;
import pl.flashgoal.users.repos.RoleRepo;
import pl.flashgoal.users.repos.UserRepo;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepo userRepository;

    @Autowired
    RoleRepo roleRepository;

    @PatchMapping("/givePremium/{username}")
    public ResponseEntity<?> givePremium(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Error: Username is not found."));

        Role premiumRole = roleRepository.findByName(EnumRole.ROLE_PREMIUM)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        Set<Role> roles = user.getRoles();
        roles.add(premiumRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Premium role added successfully to user: " + username));
    }

    @GetMapping("/getUser/{username}")
    public ResponseEntity<?> getUserData(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User Not Found with username: " + username));

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/deleteUser/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User Not Found with username: " + username));

        userRepository.delete(user);

        return ResponseEntity.ok(new MessageResponse("User deleted successfully: " + username));
    }
}