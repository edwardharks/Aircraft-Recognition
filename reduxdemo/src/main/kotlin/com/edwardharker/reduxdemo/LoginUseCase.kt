package com.edwardharker.reduxdemo

import rx.Observable
import java.util.concurrent.TimeUnit

object LoginUseCase {

    fun login(performLoginActions: Observable<PerformLoginAction>): Observable<LoginAction> =
            performLoginActions.flatMap {
                Observable.just(true)
                        .delay(2, TimeUnit.SECONDS)
                        .map { LoginSuccessAction as LoginAction }
                        .startWith(LoginLoadingAction)
            }

    fun validateEmail(emailChangedActions: Observable<EmailChangedAction>): Observable<LoginAction> =
            emailChangedActions.map { (email) ->
                if (email.contains("@", false)) EmailValidAction else EmailInvalidAction
            }

    fun validatePassword(passwordChangedActions: Observable<PasswordChangedAction>): Observable<LoginAction> =
            passwordChangedActions.map { (password) ->
                if (password.length >= 8) PasswordValidAction else PasswordInvalidAction
            }
}
