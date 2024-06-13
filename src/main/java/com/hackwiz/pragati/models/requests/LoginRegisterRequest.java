package com.hackwiz.pragati.models.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hackwiz.pragati.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRegisterRequest {

    private String name;
    private String email;
    private String password;
    private String phone;
    private UserType type;
}
