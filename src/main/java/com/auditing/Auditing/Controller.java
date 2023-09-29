package com.auditing.Auditing;


import com.auditing.Auditing.auditConfig.AuditorAwareImpl;
import com.auditing.Auditing.model.User;
import com.auditing.Auditing.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")

@RequiredArgsConstructor
public class Controller{

    private final AuditorAwareImpl auditorAware;
    private final UserRepository userRepository;

    Optional<String> currentAuditor = auditorAware.getCurrentAuditor();

    @PostMapping("/create")
    public User createUser(@Valid @RequestBody User user) {
        user = User.builder()
                .createdBy(String.valueOf(currentAuditor)).build();
        return userRepository.save(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,@Valid @RequestBody User userDetails) throws RuntimeException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found :: " + userId));
        user.setEmail(userDetails.getEmail());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        user.setLastModifiedDate(new Date());
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found :: " + userId));

        userRepository.delete(user);

        return ResponseEntity.status(HttpStatus.OK)
                .body("User deleted successfully");
    }
}
