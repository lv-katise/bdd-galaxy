package lv.katise.bdd_galaxy.di;

import io.cucumber.core.backend.ObjectFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractObjectFactory implements ObjectFactory {

    protected final Map<Class<?>, InitialisationData<?>> classes = new HashMap<>();

    public AbstractObjectFactory() {

    }

    public static boolean canInstantiateWithEmptyConstructor(Class<?> clazz, int count) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.getParameterCount() == count;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public static boolean canInstantiateWithEmptyConstructor(Class<?> clazz) {
        return canInstantiateWithEmptyConstructor(clazz, 0);
    }

    public void start() {

        startExtend();
    }

    public void stop() {
        classes.clear();
        stopExtend();
    }

    public boolean addClass(Class<?> clazz) {
        classes.put(clazz, null);
        return true;
    }


    public <T> T getInstance(Class<T> type) {
        if (classes.containsKey(type)) {
            InitialisationData<T> initialisationData = (InitialisationData<T>) classes.get(type);

            if (initialisationData == null) {
                if (canInstantiateWithEmptyConstructor(type)) {
                    try {
                        return type.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    //todo default policy
                    System.out.println("Cant initiate Class: " + type);
                    throw new RuntimeException("NOT IMPLEMENTED YET");
                }
            }

            if (initialisationData.isSingleton()) {
                if (initialisationData.getInstance() != null) {
                    return initialisationData.getInstance();
                } else {
                    T instance = initialisationData.getInitiator().apply(null);
                    initialisationData.setInstance(instance);
                    return instance;
                }
            } else {
                return initialisationData.getInitiator().apply(null);
            }
        } else {
            return getInstanceExtend(type);
        }
    }

    public boolean addClassExtend(Class<?> clazz, InitialisationData<?> initialisationData) {
        classes.put(clazz, initialisationData);
        return true;
    }

    public abstract void startExtend();

    public abstract void stopExtend();

    public abstract <T> T getInstanceExtend(Class<T> type);
}