package com.hackwiz.pragati.controller;

import com.hackwiz.pragati.models.requests.AddSkillsKycRequest;
import com.hackwiz.pragati.models.requests.LoginRegisterRequest;
import com.hackwiz.pragati.models.responses.GetProfessionalDetailsResponse;
import com.hackwiz.pragati.models.responses.GetRecruiterDetailsResponse;
import com.hackwiz.pragati.models.responses.LoginRegisterResponse;
import com.hackwiz.pragati.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static com.hackwiz.pragati.constants.ApiPaths.EDIT_PROFESSIONAL_USER_DETAILS;
import static com.hackwiz.pragati.constants.ApiPaths.GET_PROFESSIONAL_USER_DETAILS;
import static com.hackwiz.pragati.constants.ApiPaths.GET_RECRUITER_USER_DETAILS;
import static com.hackwiz.pragati.constants.ApiPaths.LOGIN_REGISTER_USER;

@Slf4j
@RestController
@RequestMapping("/v1/user")
@CrossOrigin(origins = "*")
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
            String userId = userService.loginRegisterUser(loginRegisterRequest);
            loginRegisterResponse.setSuccess(true);
            loginRegisterResponse.setUserId(userId);
        } catch (Exception ex) {
            log.error("Exception occurred while login/register user. StackTrace : {}", ex.getStackTrace());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Access-Control-Allow-Origin", "*");
//        headers.add("Vary", "Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Accept-Encoding");
        return ResponseEntity.status(httpStatus).headers(headers).body(loginRegisterResponse);
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GetMapping(GET_PROFESSIONAL_USER_DETAILS)
    public ResponseEntity<GetProfessionalDetailsResponse> getProfessionalDetails(@RequestParam String userId) {
        log.info("UserController.getProfessionalDetails: ");
        GetProfessionalDetailsResponse getProfessionalDetailsResponse = new GetProfessionalDetailsResponse();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            getProfessionalDetailsResponse = userService.getProfessionalUserDetails(userId);
        } catch (Exception ex) {
            log.error("Exception occurred while login/register user. StackTrace : {}", ex.getStackTrace());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(getProfessionalDetailsResponse);
    }


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GetMapping(GET_RECRUITER_USER_DETAILS)
    public ResponseEntity<GetRecruiterDetailsResponse> getRecruiterDetails(@RequestParam String userId) {
        log.info("UserController.getRecruiter: ");
        GetRecruiterDetailsResponse getRecruiterDetailsResponse = new GetRecruiterDetailsResponse();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            getRecruiterDetailsResponse = userService.getRecruiterDetails(userId);
        } catch (Exception ex) {
            log.error("Exception occurred while login/register user. StackTrace : {}", ex.getStackTrace());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(getRecruiterDetailsResponse);
    }


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PostMapping(EDIT_PROFESSIONAL_USER_DETAILS)
    public ResponseEntity<GetProfessionalDetailsResponse> getProfessionalDetails(@RequestBody AddSkillsKycRequest addSkillsKycRequest) {
        log.info("UserController.getProfessionalDetails: ");
        GetProfessionalDetailsResponse getProfessionalDetailsResponse = new GetProfessionalDetailsResponse();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            getProfessionalDetailsResponse = userService.addProfessionalSkills(addSkillsKycRequest);
        } catch (Exception ex) {
            log.error("Exception occurred while login/register user. StackTrace : {}", ex.getStackTrace());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(getProfessionalDetailsResponse);
    }
}
