package redux

import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject

open class Action

open class State

interface Reducer {
    fun reduce(oldState: State, action: Action): State
}

class Store(reducer: Reducer, actions: Observable<Action>) {

    private val actions = PublishSubject.create<Action>()

    private val states = actions
            .compose {
                actions.publish { actions }
            }.scan(State(), reducer::reduce)

    fun dispatch(action: Action) = actions.onNext(action)

    fun dispatch(actions: Observable<Action>): Subscription = actions.subscribe { dispatch(it) }

    fun subscribe(): Observable<State> = states
}
