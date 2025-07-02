//package com.fashionhub.backend.controller;
//
//import com.fashionhub.backend.dto.LoginRequest;
//import com.fashionhub.backend.dto.RegisterRequest;
//import com.fashionhub.backend.dto.UserResponse;
//import com.fashionhub.backend.service.AuthService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:3000")
//public class AuthController {
//
//    @Autowired
//    private AuthService authService;
//
//    @PostMapping("/login")
//    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
//        UserResponse userResponse = authService.login(loginRequest);
//        return ResponseEntity.ok(userResponse);
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
//        UserResponse userResponse = authService.register(registerRequest);
//        return ResponseEntity.ok(userResponse);
//    }
//}

package com.fashionhub.backend.controller;

import com.fashionhub.backend.dto.LoginRequest;
import com.fashionhub.backend.dto.RegisterRequest;
import com.fashionhub.backend.dto.UserResponse;
import com.fashionhub.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse userResponse = authService.register(registerRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        UserResponse userResponse = authService.login(loginRequest);
        return ResponseEntity.ok(userResponse);
    }
}

