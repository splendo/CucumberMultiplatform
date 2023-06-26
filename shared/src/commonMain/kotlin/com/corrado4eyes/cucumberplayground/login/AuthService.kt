package com.corrado4eyes.cucumberplayground.login

import com.corrado4eyes.cucumberplayground.common.model.User
import com.corrado4eyes.cucumberplayground.login.model.AuthResponse

interface AuthService {
    fun login(email: String, pass: String): AuthResponse
    fun logout()
    fun signUp(email: String, pass: String): AuthResponse
}

class AuthServiceImpl : AuthService {

    private var currentUser: User? = null

    private val users = mutableListOf(
        User("alex@alex.com", "1234"),
        User("corrado@corrado.com", "1234")
    )

    override fun login(email: String, pass: String): AuthResponse {
        if(email.isEmpty()) return AuthResponse.Error("Invalid email")
        if(pass.isEmpty()) return AuthResponse.Error("Password can not empty")
        val user = users.find { it.email == email && it.pass == pass }
        return user?.let {
            currentUser = it
            AuthResponse.Success
        } ?: AuthResponse.Error("Invalid credentials")
    }

    override fun logout() {
        currentUser = null
    }

    override fun signUp(email: String, pass: String): AuthResponse {
        if(email.isEmpty()) return AuthResponse.Error("Invalid email")
        if(pass.isEmpty()) return AuthResponse.Error("Password can not empty")
        if(pass.length < 4) return AuthResponse.Error("Password must be at least 4 characters long")
        val user = users.find { it.email == email && it.pass == pass }
        return user?.let {
            AuthResponse.Error("User already exists")
        } ?: kotlin.run {
            users.add(User(email, pass))
            AuthResponse.Success
        }
    }
}