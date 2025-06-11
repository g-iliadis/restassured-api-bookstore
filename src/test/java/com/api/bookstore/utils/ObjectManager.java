package com.api.bookstore.utils;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class ObjectManager {

    private final Map<Class<?>, Object> cache = new HashMap<>();

    public ObjectManager() {}

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> apiClass) {
        return (T) cache.computeIfAbsent(apiClass, this::newInstance);
    }

    private <T> T newInstance(Class<T> apiClass) {
        try {
            Constructor<T> ctor = apiClass.getConstructor();
            return ctor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to init API: "
                    + apiClass.getSimpleName(), e);
        }
    }
}