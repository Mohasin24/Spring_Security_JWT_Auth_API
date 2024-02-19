package com.authentication.dto;

import com.authentication.constants.AuthStatus;

public record AuthResponseDto(String token, AuthStatus authStatus)
{
}
