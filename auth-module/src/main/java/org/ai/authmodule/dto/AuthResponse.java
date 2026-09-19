package org.ai.authmodule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String accessToken;
    private String tokenType;
    private String email;
    private String role;

}
