package com.splendo.cucumberplayground.cucumber.login

import com.splendo.cucumberplayground.cucumber.login.DummyUsers.defaultTestUser
import com.splendo.cucumberplayground.login.model.AuthResponse
import com.splendo.cucumberplayground.models.User
import com.splendo.cucumberplayground.services.AuthService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Mock is the same as impl but here for the sake of proper interfacing
 */
class AuthServiceMock(loggedInUser: User?) : AuthService {

    private var currentUser: MutableStateFlow<User?> = MutableStateFlow(loggedInUser)

    private val users = mutableListOf(
        User("alex@alex.com", "1234"),
        User("corrado@corrado.com", "1234"),
        defaultTestUser
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

    var logoutCalledTimes = 0
    override suspend fun logout() {
        logoutCalledTimes++
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

    override val observeUser: Flow<User?> = currentUser.asStateFlow()

    override fun getCurrentUserIfAny(): User? {
        return currentUser.asStateFlow().value
    }
}

object DummyUsers {
    val defaultTestUser = User("test@test.com", "1234")
}
