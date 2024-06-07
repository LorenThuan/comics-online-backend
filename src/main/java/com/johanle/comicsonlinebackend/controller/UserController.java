package com.johanle.comicsonlinebackend.controller;

import com.johanle.comicsonlinebackend.dto.UserRequest;
import com.johanle.comicsonlinebackend.model.User;
import com.johanle.comicsonlinebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:5173/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserRequest> register(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.register(userRequest));
    }

    @GetMapping("/auth/refresh")
    public ResponseEntity<UserRequest> refreshToken(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.refreshToken(userRequest));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserRequest> login(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.login(userRequest));
    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<UserRequest> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserRequest> getUserById(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<UserRequest> updateUsers(@PathVariable int id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @GetMapping("/user/me")
    public ResponseEntity<UserRequest> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserRequest userRequest = userService.getMyProfile(email);
        return ResponseEntity.status(userRequest.getStatusCode()).body(userRequest);
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<UserRequest> deleteUserById(@PathVariable int userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
}
