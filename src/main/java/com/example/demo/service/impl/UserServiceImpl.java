// package com.example.demo.service.impl;

// import com.example.demo.entity.User;
// import com.example.demo.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Service;

// import java.util.Optional;
// import java.util.Set;

// @Service
// public class UserServiceImpl {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private BCryptPasswordEncoder passwordEncoder;

//     /**
//      * Create a new user
//      */
//     public User createUser(String email, String password, Set<String> roles) {

//         if (email == null || email.isBlank()) {
//             throw new IllegalArgumentException("Email required");
//         }

//         if (password == null || password.isBlank()) {
//             throw new IllegalArgumentException("Password required");
//         }

//         Optional<User> existing = userRepository.findByEmail(email);
//         if (existing.isPresent()) {
//             throw new RuntimeException("User already exists");
//         }

//         User user = new User();
//         user.setEmail(email);
//         user.setPassword(passwordEncoder.encode(password));
//         user.setRoles(roles);

//         return userRepository.save(user);
//     }

//     /**
//      * Get user by ID
//      */
//     public User getUserById(Long id) {
//         return userRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("User not found"));
//     }

//     /**
//      * Get user by email
//      */
//     public User getUserByEmail(String email) {
//         return userRepository.findByEmail(email)
//                 .orElseThrow(() -> new RuntimeException("User not found"));
//     }

//     /**
//      * Delete user
//      */
//     public void deleteUser(Long id) {
//         userRepository.deleteById(id);
//     }
// }
