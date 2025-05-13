package com.assignment.auth.model.request;

import com.assignment.core.model.RequestData;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest  extends RequestData {
    @NotBlank(message = "Refresh token must not Blank")
    private String refreshToken;
}
