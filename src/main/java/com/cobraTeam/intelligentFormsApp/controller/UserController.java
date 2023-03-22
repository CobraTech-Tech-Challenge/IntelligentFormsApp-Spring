package com.cobraTeam.intelligentFormsApp.controller;

import com.azure.cosmos.models.PartitionKey;
import com.cobraTeam.intelligentFormsApp.entity.User;
import com.cobraTeam.intelligentFormsApp.repository.UserRepository;
import com.cobraTeam.intelligentFormsApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

//    @PostMapping("/create")
//    public ResponseEntity<User> createNewUser(@RequestBody User user) {
//        return ResponseEntity.ok(userService.create(user));
//    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }





//    @GetMapping
//    public List<User> getUsers() {
//       return List.of(new User("Codrut", "Arotaritei", 20, "at.codrut@gmail.com", "Suceava", "programmer"),
//                new User("Mihai", "Ion", 30, "m.ion@gmail.com", "Botosani", "QA Testing"));
//    }
}
