package com.example.API_Gateway.controller;

import com.example.API_Gateway.controller.request.LoginRequest;
import com.example.API_Gateway.controller.request.RegisterRequest;
import com.example.API_Gateway.controller.response.LoginResponse;
import com.example.API_Gateway.factory.ResponseFactory;
import com.example.API_Gateway.repository.entity.UserEntity;
import com.example.API_Gateway.service.UserService;
import com.example.API_Gateway.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final ResponseFactory responseFactory;

    @PostMapping("/auth/login")
    public ResponseEntity login(@RequestBody LoginRequest request){
        log.info("start logging in");
        UserEntity userEntity = userService.findUserByUsername(request.getUsername());
        if (userEntity == null){
            log.error("user not found");
            return responseFactory.error(HttpStatus.NOT_FOUND, "User Not Found");
        }
        if (!userService.validatePassword(userEntity, request.getPassword())){
            log.error("password incorrect");
            return  responseFactory.error(HttpStatus.BAD_REQUEST, "Invalid Information");

        }

        String token = jwtUtil.generateToken(userEntity.getUsername(), "ROLE_USER");
        LoginResponse response = new LoginResponse(token);
        log.info("login success");
        return responseFactory.success(response, LoginResponse.class);

    }

    @PostMapping("/auth/register")
    public ResponseEntity register(@RequestBody RegisterRequest request){
        log.info("start register");
        UserEntity userEntity = userService.findUserByUsername(request.getUsername());
        if(userEntity != null) {
            log.error("username already exist");
            return responseFactory.error(HttpStatus.BAD_REQUEST, "Invalid Information");
        }
        userService.saveNewUser(request.getUsername(), request.getPassword());
        log.info("register success");
        return responseFactory.success();
    }
}
