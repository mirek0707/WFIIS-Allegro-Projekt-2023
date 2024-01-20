package pl.flashgoal.users.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import pl.flashgoal.users.models.EnumRole;
import pl.flashgoal.users.models.Role;
import pl.flashgoal.users.models.User;
import pl.flashgoal.users.payload.response.MessageResponse;
import pl.flashgoal.users.repos.RoleRepo;
import pl.flashgoal.users.repos.UserRepo;
import pl.flashgoal.users.security.jwt.JwtUtils;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepo userRepository;

    @Autowired
    RoleRepo roleRepository;

    @Autowired
    JwtUtils jwtUtils;

    private final RestTemplate restTemplate = new RestTemplate();
    @PostMapping("/givePremium/{username}")
    public ResponseEntity<?> givePremium(@PathVariable String username, @RequestHeader("Authorization") String userToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", userToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> userResponse;
            boolean isAdmin = false;
            try {
                userResponse = restTemplate.exchange("http://localhost:8084/api/test/admin", HttpMethod.GET, entity, String.class);
                isAdmin = userResponse.getStatusCode() == HttpStatus.OK;
            }catch(RuntimeException e){
                System.out.println("User is not an admin");
            };

            if(!isAdmin){
                String jwtToken = userToken.substring(7);
                String usernameFromToken = jwtUtils.getUserNameFromJwtToken(jwtToken);

                boolean isPermittedUser = username.equals(usernameFromToken);

                if(!isPermittedUser){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user for this operation");
                }
            }

            System.out.println(isAdmin);
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Error: Username is not found."));

            Role premiumRole = roleRepository.findByName(EnumRole.ROLE_PREMIUM)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

            Set<Role> roles = user.getRoles();

            if (roles.stream().anyMatch(role -> role.getName().equals(EnumRole.ROLE_PREMIUM))) {
                return ResponseEntity.badRequest().body(new MessageResponse("User already has the Premium role."));
            }

            roles.add(premiumRole);

            user.setRoles(roles);
            userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("Premium role added successfully to user: " + username));
        }catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getUser/{username}")
    public ResponseEntity<?> getUserData(@PathVariable String username, @RequestHeader("Authorization") String userToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", userToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> userResponse;
            boolean isAdmin = false;
            try {
                userResponse = restTemplate.exchange("http://localhost:8084/api/test/admin", HttpMethod.GET, entity, String.class);
                isAdmin = userResponse.getStatusCode() == HttpStatus.OK;
            }catch(RuntimeException e){
                System.out.println("User is not an admin");
            };

            if(!isAdmin){
                String jwtToken = userToken.substring(7);
                String usernameFromToken = jwtUtils.getUserNameFromJwtToken(jwtToken);

                boolean isPermittedUser = username.equals(usernameFromToken);

                if(!isPermittedUser){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user for this operation");
                }
            }

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User Not Found with username: " + username));

            return ResponseEntity.ok(user);
        }catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteUser/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username, @RequestHeader("Authorization") String userToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", userToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> userResponse;
            boolean isAdmin = false;
            try {
                userResponse = restTemplate.exchange("http://localhost:8084/api/test/admin", HttpMethod.GET, entity, String.class);
                isAdmin = userResponse.getStatusCode() == HttpStatus.OK;
            }catch(RuntimeException e){
                System.out.println("User is not an admin");
            };

            if(!isAdmin){
                String jwtToken = userToken.substring(7);
                String usernameFromToken = jwtUtils.getUserNameFromJwtToken(jwtToken);

                boolean isPermittedUser = username.equals(usernameFromToken);

                if(!isPermittedUser){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user for this operation");
                }
            }

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User Not Found with username: " + username));

            userRepository.delete(user);

            return ResponseEntity.ok(new MessageResponse("User deleted successfully: " + username));
        } catch(RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}