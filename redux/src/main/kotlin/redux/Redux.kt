package redux

import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject

open class Action

open class State

interface Reducer<S : State> {
    fun reduce(oldState: S, action: Action): S
}

interface ActionTransformer {
    fun transform(actions: Observable<Action>): Observable<Action>
}

object NoOpActionTransformer : ActionTransformer {
    override fun transform(actions: Observable<Action>): Observable<Action> = actions
}

class Store<S : State>(initialValue: S,
                       reducer: Reducer<S>,
                       actionTransformer: ActionTransformer = NoOpActionTransformer) {

    private val actionsSubject: PublishSubject<Action> = PublishSubject.create<Action>()

    private val states: Observable<S> = actionsSubject
            .compose { actionsSubject.publish(actionTransformer::transform) }
            .scan(initialValue, reducer::reduce)
            .distinctUntilChanged()

    fun dispatch(action: Action) = actionsSubject.onNext(action)

    fun dispatch(actions: Observable<Action>): Subscription = actions.subscribe { dispatch(it) }

    fun subscribe(): Observable<S> = states
}
