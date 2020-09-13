package com.yanm.common;

import java.util.LinkedList;
import java.util.List;

public class Property<T> {

    private T value;
    private List<SimpleChangeListener<T>> listeners = new LinkedList<>();

    public Property(T value) {
        this.value = value;
    }

    public Property() {
        this(null);
    }

    public void listen(SimpleChangeListener<T> listener) {
        listeners.add(listener);
    }

    public void set(T newValue) {
        value = newValue;
        notifyListeners();
    }

    public T get() {
        return value;
    }

    public boolean isPresent() {
        return value != null;
    }

    private void notifyListeners() {
        for (SimpleChangeListener<T> listener : listeners) {
            listener.valueChanged(value);
        }
    }
}
