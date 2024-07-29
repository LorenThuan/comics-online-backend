package com.johanle.comicsonlinebackend.controller;

import com.johanle.comicsonlinebackend.dto.RemoveFromLibRequest;
import com.johanle.comicsonlinebackend.dto.UserRequest;
import com.johanle.comicsonlinebackend.model.Comic;
import com.johanle.comicsonlinebackend.model.User;
import com.johanle.comicsonlinebackend.repository.UserRepository;
import com.johanle.comicsonlinebackend.service.UserService;
import org.hibernate.Remove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:5173/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/users/get-user-members")
    public ResponseEntity<UserRequest> getAllUserMembers() {
        return ResponseEntity.ok(userService.getAllUserMembers());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserRequest> getUserById(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/admin/update/{id}")
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

    @GetMapping("/users/")
    public ResponseEntity<UserRequest> findUserByName(@RequestParam String name) {
        return ResponseEntity.ok(userService.findUserByName(name));
    }

    @PostMapping("/titles/follows")
    public ResponseEntity<User> addToLibrary(@RequestParam int comic_id) {
        System.out.println("Received comicId: " + comic_id);  // Debugging line
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            System.out.println("Authentication is null");  // Debugging line
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String email = authentication.getName();
        User user = userService.addToLibrary(comic_id, email);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/remove/comics/{userId}")
    public ResponseEntity<User> removeComicFromLibrary
            (@PathVariable int userId, @RequestBody RemoveFromLibRequest removeFromLibRequest) {
        User userRequest = userService.
                removeFromLibrary(userId, removeFromLibRequest.getComicList(), removeFromLibRequest.getComicListRemove());
        return ResponseEntity.ok(userRequest);
    }

}
