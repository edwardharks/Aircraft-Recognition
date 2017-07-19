package redux

import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject

open class Action

open class State

interface Reducer<S : State> {
    fun reduce(oldState: S, action: Action): S
}

class Store<S : State>(initialValue: S,
                       reducer: Reducer<S>) {

    private val actionsSubject: PublishSubject<Action> = PublishSubject.create<Action>()

    private val states: Observable<S> = actionsSubject
            .scan(initialValue, reducer::reduce)
            .distinctUntilChanged()

    fun dispatch(action: Action) = actionsSubject.onNext(action)

    fun dispatch(actions: Observable<Action>): Subscription = actions.subscribe { dispatch(it) }

    fun subscribe(): Observable<S> = states
}
