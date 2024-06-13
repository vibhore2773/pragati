package com.hackwiz.pragati.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRegisterResponse {

    private boolean success;
    private String errorMessage;
}
