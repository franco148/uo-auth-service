package com.umssonline.auth.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

@Repository
public class HibernateDaoImpl implements AuthDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private EntityManager entityManager;


    @Autowired
    public HibernateDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public <T> T persist(T entity) {
        entityManager.persist(entity);

        return entity;
    }

    @Transactional
    @Override
    public <T> Collection<T> persist(T[] entities) {
        for (T entity : entities) {
            entityManager.persist(entity);
        }

        return Arrays.asList(entities);
    }

    @Override
    public <T> T update(T entity) {
        logger.info("Contiene la entidad...." + entityManager.contains(entity));
        if(!entityManager.contains(entity)) {
            entityManager.merge(entity);
        }
        entityManager.persist(entity);
        return entity;
    }

    @Transactional(readOnly = true)
    @Override
    public <T> Collection<T> load(Class<T> entityClass) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> rootEntry = cq.from(entityClass);

        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);

        return allQuery.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public <T> T find(Class<T> entityClass, Serializable id) {
        return entityManager.find(entityClass, id);
    }

    @Transactional(readOnly = true)
    @Override
    public <T> Collection<T> load(String jpql, Class<T> entityClass) {
        TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
        return query.getResultList();
    }

    @Transactional
    @Override
    public <T> Boolean delete(T entity) {
        entityManager.remove(entity);
        return true;
    }
}
