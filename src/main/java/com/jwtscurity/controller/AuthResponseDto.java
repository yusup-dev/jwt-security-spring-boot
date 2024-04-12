package com.jwtscurity.controller;


public record AuthResponseDto(
        String token,
        AuthStatus authStatus
) {
}
