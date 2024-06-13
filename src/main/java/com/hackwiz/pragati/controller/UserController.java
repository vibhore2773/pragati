package com.hackwiz.pragati.controller;

import com.hackwiz.pragati.models.requests.LoginRegisterRequest;
import com.hackwiz.pragati.models.responses.LoginRegisterResponse;
import com.hackwiz.pragati.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static com.hackwiz.pragati.constants.ApiPaths.LOGIN_REGISTER_USER;

@Slf4j
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PostMapping(value = LOGIN_REGISTER_USER)
    public ResponseEntity<LoginRegisterResponse> loginRegisterUser(@RequestBody LoginRegisterRequest loginRegisterRequest) {
        log.info("UserController.loginRegisterUser: loginRegisterRequest={}", loginRegisterRequest);
        LoginRegisterResponse loginRegisterResponse = new LoginRegisterResponse();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            userService.loginRegisterUser(loginRegisterRequest);
            loginRegisterResponse.setSuccess(true);
        } catch (Exception ex) {
            log.error("Exception occurred while login/register user. StackTrace : {}", ex.getStackTrace());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(loginRegisterResponse);
    }

}
