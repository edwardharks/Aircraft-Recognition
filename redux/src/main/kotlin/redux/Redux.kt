package redux

import rx.Emitter
import rx.Observable
import rx.Subscription

interface Action

typealias Unsubscriber = () -> Unit

class Store<S>(private val reducer: (S, Action) -> S, initialState: S) {
    var state: S = initialState
        private set
    private val listeners = HashSet<(S) -> Unit>()
    private var isDispatching = false
    private val lock = Object()

    fun dispatch(action: Action) {
        synchronized(lock) {
            if (isDispatching) {
                throw IllegalStateException("Reducers may not dispatch actions.")
            }

            try {
                isDispatching = true
                state = reducer(state, action)
            } finally {
                isDispatching = false
            }
        }

        for (listener in listeners) {
            listener(state)
        }
    }


    fun subscribe(listener: (S) -> Unit): Unsubscriber {
        listeners.add(listener)
        listener(state)

        return fun() {
            listeners.remove(listener)
        }
    }

    companion object {
        fun <S> create(reducer: (S, Action) -> S, initialState: S): Store<S> =
            Store(reducer, initialState)
    }
}

fun <S> Store<S>.dispatch(actions: Observable<out Action>): Subscription {
    return actions.subscribe { dispatch(it) }
}

fun <S> Store<S>.observe(): Observable<out S> {
    return Observable.create({ emitter ->
        val unsubscriber = subscribe { emitter.onNext(it) }
        emitter.setCancellation(unsubscriber)
    }, Emitter.BackpressureMode.DROP)
}
