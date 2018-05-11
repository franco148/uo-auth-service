package com.umssonline.auth.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

public class HibernateDaoImpl implements AuthDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public <T> T persist(T entity) {
        return null;
    }

    @Override
    public <T> Collection<T> persist(T[] entities) {
        return null;
    }

    @Override
    public <T> Collection<T> load(Class<T> entityClass) {
        return null;
    }

    @Override
    public <T> T find(Class<T> entityClass, Serializable id) {
        return null;
    }

    @Override
    public <T> Collection<T> load(String jpql) {
        return null;
    }

    @Override
    public <T> Boolean delete(T entity) {
        return null;
    }
}
