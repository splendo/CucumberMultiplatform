import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object Strings {
    val loginScreenTitle = "Login screen"
    val homeScreenTitle = "Home screen"

    val loginScreenTag = "Login"
    val homeScreenTag = "Home"

    val loginButtonText = "Login"
    val logoutButtonText = "Logout"

    val emailTextFieldTag = "Email"
    val passwordTextFieldTag = "Password"

    val emailTextFieldPlaceholder = "Email"
    val passwordTextFieldPlaceholder = "Password"
}