package lv.katise.bdd_galaxy.di;

import java.util.function.Function;

public class InitialisationData<T> {

    private final boolean isSingleton;

    private final Function<Object, T> initiator;

    private T instance;

    public InitialisationData(boolean isSingleton, Function<Object, T> initiator) {
        this.isSingleton = isSingleton;
        this.initiator = initiator;
    }


    public boolean isSingleton() {
        return isSingleton;
    }

    public Function<Object, T> getInitiator() {
        return initiator;
    }

    public T getInstance() {
        return instance;
    }

    public void setInstance(T instance) {
        this.instance = instance;
    }
}
