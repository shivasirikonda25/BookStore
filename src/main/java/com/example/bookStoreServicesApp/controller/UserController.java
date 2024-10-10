package com.example.bookStoreServicesApp.controller;

import com.example.bookStoreServicesApp.exception.InvalidUserNameOrPassword;
import com.example.bookStoreServicesApp.model.User;
import com.example.bookStoreServicesApp.service.UserService;
import com.example.bookStoreServicesApp.util.JwtService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/user")
@Validated
public class UserController {
    @Autowired
    private UserService adminService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (!existingUser.isPresent()) {
            userService.registerUser(user);
            Map<String,String> map= new HashMap<>();
            map.put("Message",user.getRole()+" registered Successfully :)");
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        else{
            return ResponseEntity.badRequest().body("Username is already taken");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) throws InvalidUserNameOrPassword {
        Optional<User> existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isPresent() && passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            String token = jwtService.generateToken(existingUser.get().getRole(),existingUser.get().getId());
            Map<String,String> map= new HashMap<>();
            map.put("token",token);
            return ResponseEntity.ok(map);
        } else {
            throw new InvalidUserNameOrPassword("Invalid Email or password.");
        }
    }


}
