package com.edwardharker.reduxdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.jakewharton.rxbinding.view.RxView
import com.jakewharton.rxbinding.widget.RxTextView
import redux.Action
import redux.Store
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription

class LoginActivity : AppCompatActivity() {

    val emailField by bind<EditText>(R.id.email)
    val emailFieldContainer by bind<TextInputLayout>(R.id.email_container)
    val passwordField by bind<EditText>(R.id.password)
    val passwordFieldContainer by bind<TextInputLayout>(R.id.password_container)
    val progressView by bind<View>(R.id.login_progress)
    val loginButton by bind<Button>(R.id.email_sign_in_button)

    private lateinit var store: Store<LoginState>
    private val disposables = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        store = Store(LoginState(), LoginReducer, LoginActionsTransformer)
    }

    override fun onStart() {
        super.onStart()

        val emailChangedEvents = RxTextView.afterTextChangeEvents(emailField)
                .observeOn(AndroidSchedulers.mainThread())
                .map { EmailChangedAction(emailField.text.toString()) }

        val passwordChangedEvents = RxTextView.afterTextChangeEvents(passwordField)
                .observeOn(AndroidSchedulers.mainThread())
                .map { PasswordChangedAction(passwordField.text.toString()) }

        val loginClickedEvents = RxView.clicks(loginButton)
                .map { PerformLoginAction(emailField.text.toString(), passwordField.text.toString()) }

        val events: Observable<Action> = Observable.merge(emailChangedEvents, passwordChangedEvents, loginClickedEvents)

        store.dispatch(events)

        disposables.add(store.subscribe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { (isLoggedIn, enableLoginButton, invalidEmail, invalidPassword, isLoading) ->
                    progressView.visibility = if (isLoading) View.VISIBLE else View.GONE

                    if (isLoggedIn) {
                        Toast.makeText(this, "Logged in", Toast.LENGTH_LONG).show()
                    }

                    loginButton.isEnabled = enableLoginButton

                    emailField.isEnabled = !isLoading
                    passwordField.isEnabled = !isLoading

                    emailFieldContainer.error = if (invalidEmail) "Invalid email" else null
                    passwordFieldContainer.error = if (invalidPassword) "Invalid password" else null
                })
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }
}
