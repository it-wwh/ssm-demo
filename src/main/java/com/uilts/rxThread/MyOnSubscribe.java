package com.uilts.rxThread;

import org.reactivestreams.Subscriber;

public abstract class MyOnSubscribe<C> implements Subscriber<C> {
    private C c;

    public MyOnSubscribe(C c) {
        setT(c);
    }

    public C getT() {
        return c;
    }

    public void setT(C c) {
        this.c = c;
    }


}
