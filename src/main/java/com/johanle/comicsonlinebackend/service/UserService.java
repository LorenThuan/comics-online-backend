package com.johanle.comicsonlinebackend.service;

import com.johanle.comicsonlinebackend.dto.UserRequest;
import com.johanle.comicsonlinebackend.model.User;
import com.johanle.comicsonlinebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserRequest register(UserRequest registrationRequest){
        UserRequest userRequest = new UserRequest();
        try {
            User user = new User();
            user.setUsername(registrationRequest.getUsername());
            user.setEmail(registrationRequest.getEmail());
            user.setRole(registrationRequest.getRole());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            User saveResult = userRepository.save(user);
            if (saveResult.getUserId() > 0) {
                userRequest.setUser(saveResult);
                userRequest.setMessage("User Saved Successfully");
                userRequest.setStatusCode(200);
            }
        } catch (Exception e) {
            userRequest.setStatusCode(500);
            userRequest.setError(e.getMessage());
        }
        return userRequest;
    }

    public UserRequest login(UserRequest loginRequest) {
        UserRequest userRequest = new UserRequest();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken
                            (loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            userRequest.setStatusCode(200);
            userRequest.setToken(jwt);
            userRequest.setRefreshToken(refreshToken);
            userRequest.setExpirationTime("24Hrs");
            userRequest.setMessage("Successfully login");

        } catch (Exception e) {
            userRequest.setStatusCode(500);
            userRequest.setError(e.getMessage());
        }
        return userRequest;
    }

    public UserRequest refreshToken(UserRequest refreshTokenRequest) {
        UserRequest userRequest = new UserRequest();
        try {
            String email = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            User user = userRepository.findByEmail(email).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
                var jwt = jwtUtils.generateToken(user);
                userRequest.setStatusCode(200);
                userRequest.setToken(jwt);
                userRequest.setRefreshToken(refreshTokenRequest.getToken());
                userRequest.setExpirationTime("24Hrs");
                userRequest.setMessage("Successfully Refresh Token");
            }
            userRequest.setStatusCode(200);
            return userRequest;
        } catch (Exception e) {
            userRequest.setStatusCode(500);
            userRequest.setError(e.getMessage());
        }
        return userRequest;
    }

    public UserRequest getAllUsers(){
        UserRequest userRequest = new UserRequest();
        try {
            List<User> userList = userRepository.findAll();
            if (!userList.isEmpty()) {
                userRequest.setUserList(userList);
                userRequest.setStatusCode(200);
                userRequest.setMessage("Successfully");
            } else {
                userRequest.setStatusCode(404);
                userRequest.setMessage("No users found");
            }
            return userRequest;
        } catch (Exception e) {
            userRequest.setStatusCode(500);
            userRequest.setError("Error occurred: "+ e.getMessage());
            return userRequest;
        }
    }

    public UserRequest getUserById(int id) {
        UserRequest userRequest = new UserRequest();
        try {
            User userById = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            userRequest.setUser(userById);
            userRequest.setStatusCode(200);
            userRequest.setMessage("Found user successfully");
        } catch (Exception e) {
            userRequest.setStatusCode(500);
            userRequest.setMessage(e.getMessage());
        }
        return userRequest;
    }

    public UserRequest updateUser(int id, User updateUser) {
        UserRequest userRequest = new UserRequest();
        try {
            Optional<User> userOptinal = userRepository.findById(id);
            if (userOptinal.isPresent()) {
                User existingUser = userOptinal.get();
                existingUser.setEmail(updateUser.getEmail());
                existingUser.setUsername(updateUser.getUsernameEntity());
                
                /*Check if password is present in the request*/
                if (updateUser.getPassword() != null && !updateUser.getPassword().isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
                }

                User saveUser = userRepository.save(existingUser);
                userRequest.setUser(saveUser);
                userRequest.setStatusCode(200);
                userRequest.setMessage("Update successfully");
            }
        } catch (Exception e) {
            userRequest.setStatusCode(500);
            userRequest.setMessage(e.getMessage());
        }
        return userRequest;
    }

    public UserRequest deleteUser(int id) {
        UserRequest userRequest = new UserRequest();
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                userRepository.deleteById(id);
                userRequest.setStatusCode(200);
                userRequest.setMessage("Delete successfully");
            } else {
                userRequest.setStatusCode(404);
                userRequest.setMessage("Can't found user");
            }
        } catch (Exception e) {
            userRequest.setStatusCode(500);
            userRequest.setMessage("Error occurred while deleting user " + e.getMessage());
        }
        return userRequest;
    }

    public UserRequest getMyProfile(String email) {
        UserRequest userRequest = new UserRequest();
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                userRequest.setUser(userOptional.get());
                userRequest.setStatusCode(200);
                userRequest.setMessage("Get successfully");
            } else {
                userRequest.setStatusCode(404);
                userRequest.setMessage("Can't found user");
            }
        } catch (Exception e) {
            userRequest.setStatusCode(500);
            userRequest.setMessage("Error occurred while get your profile " + e.getMessage());
        }
        return userRequest;
    }

}
