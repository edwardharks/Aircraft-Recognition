package com.edwardharker.reduxdemo

import redux.Action
import redux.Reducer
import redux.State

sealed class LoginAction : Action()
data class EmailChangedAction(val email: String) : LoginAction()
data class PasswordChangedAction(val password: String) : LoginAction()
data class PerformLoginAction(val email: String, val password: String) : LoginAction()
object LoginSuccessAction : LoginAction()
object LoginLoadingAction : LoginAction()
object EmailValidAction : LoginAction()
object EmailInvalidAction : LoginAction()
object PasswordValidAction : LoginAction()
object PasswordInvalidAction : LoginAction()

data class LoginState(val isLoggedIn: Boolean = false,
                      val enableLoginButton: Boolean = false,
                      val invalidEmail: Boolean = false,
                      val invalidPassword: Boolean = false,
                      val isLoading: Boolean = false,
                      val loginFormVisible: Boolean = true) : State()

object LoginReducer : Reducer<LoginState> {

    override fun reduce(oldState: LoginState, action: Action): LoginState =
            when (action) {
                is LoginLoadingAction -> LoginState(isLoading = true, enableLoginButton = false, loginFormVisible = false)
                is LoginSuccessAction -> LoginState(isLoggedIn = true, enableLoginButton = false, loginFormVisible = false)
                is EmailValidAction -> oldState.copy(invalidEmail = false, enableLoginButton = !oldState.invalidPassword)
                is EmailInvalidAction -> oldState.copy(invalidEmail = true, enableLoginButton = false)
                is PasswordValidAction -> oldState.copy(invalidPassword = false, enableLoginButton = !oldState.invalidEmail)
                is PasswordInvalidAction -> oldState.copy(invalidPassword = true, enableLoginButton = false)
                else -> oldState
            }
}
