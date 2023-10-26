package com.splendo.cucumberplayground.login.model

sealed class AuthResponse {
    object Success : AuthResponse()
    data class Error(val error: String) : AuthResponse()
}