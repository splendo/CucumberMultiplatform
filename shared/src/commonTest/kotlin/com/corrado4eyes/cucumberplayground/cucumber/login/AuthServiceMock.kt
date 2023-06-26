package com.corrado4eyes.cucumberplayground.cucumber.login

import com.corrado4eyes.cucumberplayground.common.model.User
import com.corrado4eyes.cucumberplayground.login.AuthService
import com.corrado4eyes.cucumberplayground.login.model.AuthResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Mock is the same as impl but here for the sake of proper interfacing
 */
class AuthServiceMock : AuthService {

    private var currentUser: MutableStateFlow<User?> = MutableStateFlow(null)

    private val users = mutableListOf(
        User("alex@alex.com", "1234"),
        User("corrado@corrado.com", "1234")
    )

    override suspend fun login(email: String, pass: String): AuthResponse {
        delay(2000)
        if (email.isEmpty()) return AuthResponse.Error("Invalid email")
        if (pass.isEmpty()) return AuthResponse.Error("Password can not empty")
        val user = users.find { it.email == email && it.pass == pass }
        return user?.let {
            currentUser.value = it
            AuthResponse.Success
        } ?: AuthResponse.Error("Invalid credentials")
    }

    override suspend fun logout() {
        delay(1000)
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

    override fun observeUser(): Flow<User?> {
        return currentUser.asStateFlow()
    }
}
