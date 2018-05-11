package com.umssonline.auth.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

public class HibernateDaoImpl implements AuthDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private EntityManager entityManager;



    @Override
    public <T> T persist(T entity) {
        entityManager.persist(entity);

        return entity;
    }

    @Override
    public <T> Collection<T> persist(T[] entities) {
        for (T entity : entities) {
            entityManager.persist(entity);
        }

        return Arrays.asList(entities);
    }

    @Override
    public <T> Collection<T> load(Class<T> entityClass) {
        //String selectAllQuery = "SELECT e FROM "
        return null;
    }

    @Override
    public <T> T find(Class<T> entityClass, Serializable id) {
        return entityManager.find(entityClass, id);
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
