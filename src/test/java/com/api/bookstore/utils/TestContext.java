package com.api.bookstore.utils;

import com.api.bookstore.factory.TestDataFactory;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private final Map<String, Object> data = new HashMap<>();

    private ObjectManager mgr;

    private TestDataFactory dataFactory;

    public TestContext() {

    }


    public void set(String key, Object value) {
        data.put(key, value);
    }

    public <T> T get(String key, Class<T> clazz) {
        return clazz.cast(data.get(key));
    }

    public ObjectManager getPageObjectManager() {
        return mgr;
    }

    public void setPageObjectManager(ObjectManager manager) {
        this.mgr = manager;
    }

    public TestDataFactory getDataFactory() {
        return dataFactory;
    }

    public void setDataFactory(TestDataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }

}
