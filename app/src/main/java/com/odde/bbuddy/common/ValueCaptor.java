package com.odde.bbuddy.common;

public class ValueCaptor<T> {

    private T t;

    public ValueCaptor(T t) {
        this.t = t;
    }

    public void capture(T t) {
        this.t = t;
    }

    public T value() {
        return t;
    }
}
