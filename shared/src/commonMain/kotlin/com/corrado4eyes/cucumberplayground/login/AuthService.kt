package com.corrado4eyes.cucumberplayground.login

import com.corrado4eyes.cucumberplayground.models.User
import com.corrado4eyes.cucumberplayground.login.model.AuthResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface AuthService {
    suspend fun login(email: String, pass: String): AuthResponse
    suspend fun logout()
    suspend fun signUp(email: String, pass: String): AuthResponse
    val user: Flow<User?>
}

class AuthServiceImpl : AuthService {

    private var currentUser: MutableStateFlow<User?> = MutableStateFlow(null)
    override val user: Flow<User?> = currentUser.asStateFlow()

    private val users = mutableListOf(
        User("alex@alex.com", "1234"),
        User("corrado@corrado.com", "1234"),
        User("test@test.com", "1234")
    )

    override suspend fun login(email: String, pass: String): AuthResponse {
        if (email.isEmpty()) return AuthResponse.Error("Invalid email")
        if (pass.isEmpty()) return AuthResponse.Error("Password can not empty")
        val user = users.find { it.email == email && it.pass == pass }
        return user?.let {
            currentUser.value = it
            AuthResponse.Success
        } ?: AuthResponse.Error("Invalid credentials")
    }

    override suspend fun logout() {
        currentUser.value = null
    }

    override suspend fun signUp(email: String, pass: String): AuthResponse {
        delay(2000)
        if (email.isEmpty()) return AuthResponse.Error("Invalid email")
        if (pass.isEmpty()) return AuthResponse.Error("Password can not empty")
        if (pass.length < 4) return AuthResponse.Error("Password must be at least 4 characters long")
        val user = users.find { it.email == email && it.pass == pass }
        return user?.let {
            AuthResponse.Error("User already exists")
        } ?: kotlin.run {
            users.add(User(email, pass))
            AuthResponse.Success
        }
    }
}
