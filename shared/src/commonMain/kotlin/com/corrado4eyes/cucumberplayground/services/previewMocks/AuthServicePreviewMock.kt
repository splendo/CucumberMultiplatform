package com.corrado4eyes.cucumberplayground.services.previewMocks

import com.corrado4eyes.cucumberplayground.models.User
import com.corrado4eyes.cucumberplayground.login.model.AuthResponse
import com.corrado4eyes.cucumberplayground.services.AuthService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthServicePreviewMock : AuthService {

    private var currentUser: MutableStateFlow<User?> =
        MutableStateFlow(User("test@test.com", "1234"))
    override val observeUser: Flow<User?> = currentUser.asStateFlow()

    private val databaseUsers = mutableListOf(
        User("alex@alex.com", "1234"),
        User("corrado@corrado.com", "1234"),
        User("test@test.com", "1234")
    )

    override fun getCurrentUserIfAny(): User? = currentUser.value

    override suspend fun login(email: String, pass: String): AuthResponse {
        if (email.isEmpty()) return AuthResponse.Error("Invalid email")
        if (pass.isEmpty()) return AuthResponse.Error("Password can not empty")
        val user = databaseUsers.find { it.email == email && it.pass == pass }
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
        val user = databaseUsers.find { it.email == email && it.pass == pass }
        return user?.let {
            AuthResponse.Error("User already exists")
        } ?: kotlin.run {
            databaseUsers.add(User(email, pass))
            AuthResponse.Success
        }
    }
}
