package com.andersonpimentel.faceitdata.baseviewmodel.viewmodel

import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.andersonpimentel.faceitdata.baseviewmodel.action.ViewAction
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class SingleEventLiveData<Action: ViewAction> : MutableLiveData<Action>() {

    private val pendingNotification = AtomicBoolean(false)
    private val observersCount = AtomicInteger(0)
    private val notifyCount = AtomicInteger(0)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in Action>) {
        observersCount.getAndIncrement()
        super.observe(owner){ data ->
            val hasPendingNotification: Boolean = (notifyCount.decrementAndGet() > 0)
            if (pendingNotification.getAndSet(hasPendingNotification)) {
                observer.onChanged(data)
            }
        }
    }

    @MainThread
    override fun setValue(@Nullable value: Action?) {
        pendingNotification.set(true)
        notifyCount.set(observersCount.get())
        super.setValue(value)
    }

    override fun getValue(): Action? {
        throw UnsupportedOperationException("Should be used only 'observe' method for get values")
    }

    @MainThread
    override fun removeObserver(observer: Observer<in Action>) {
        super.removeObserver(observer)
        observersCount.getAndDecrement()
    }
}
