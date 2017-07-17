package redux

import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject

open class Action

open class State

interface Reducer {
    fun reduce(oldState: State, action: Action): State
}

class Store(reducer: Reducer, actions: (Observable<Action>) -> Observable<Action>) {

    private val actionsSubject = PublishSubject.create<Action>()

    private val states = actionsSubject
            .compose {
                actionsSubject.publish(actions)
            }.scan(State(), reducer::reduce)

    fun dispatch(action: Action) = actionsSubject.onNext(action)

    fun dispatch(actions: Observable<Action>): Subscription = actions.subscribe { dispatch(it) }

    fun subscribe(): Observable<State> = states
}
