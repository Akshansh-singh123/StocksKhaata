package com.akshansh.stockskhaata.common;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseObservable<ListenerType> {
    private final Set<ListenerType> listeners = new HashSet<>();

    public final void registerListeners(ListenerType listener){
        listeners.add(listener);
    }

    public final void unregisterListeners(ListenerType listenerType){
        listeners.remove(listenerType);
    }

    public Set<ListenerType> getListeners() {
        return Collections.unmodifiableSet(listeners);
    }
}
