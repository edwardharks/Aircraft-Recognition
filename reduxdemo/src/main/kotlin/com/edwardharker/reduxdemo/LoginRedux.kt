package com.edwardharker.reduxdemo

import redux.Action
import redux.ActionTransformer
import redux.Reducer
import redux.State
import rx.Observable

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
                      val isLoading: Boolean = false) : State()

object LoginReducer : Reducer<LoginState> {

    override fun reduce(oldState: LoginState, action: Action): LoginState {
        when (action) {
            is LoginLoadingAction -> return LoginState(isLoading = true, enableLoginButton = false)
            is LoginSuccessAction -> return LoginState(isLoggedIn = true, enableLoginButton = false)
            is EmailValidAction -> return oldState.copy(invalidEmail = false, enableLoginButton = !oldState.invalidPassword)
            is EmailInvalidAction -> return oldState.copy(invalidEmail = true, enableLoginButton = false)
            is PasswordValidAction -> return oldState.copy(invalidPassword = false, enableLoginButton = !oldState.invalidEmail)
            is PasswordInvalidAction -> return oldState.copy(invalidPassword = true, enableLoginButton = false)
        }
        return oldState
    }
}

object LoginActionsTransformer : ActionTransformer {

    override fun transform(actions: Observable<Action>): Observable<Action> =
            Observable.merge(actions.ofType(PerformLoginAction::class.java).compose(LoginUseCase::login),
                    actions.ofType(EmailChangedAction::class.java).compose(LoginUseCase::validateEmail),
                    actions.ofType(PasswordChangedAction::class.java).compose(LoginUseCase::validatePassword))


}