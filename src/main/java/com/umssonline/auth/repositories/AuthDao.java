package com.umssonline.auth.repositories;

import java.io.Serializable;
import java.util.Collection;

public interface AuthDao {

    <T> T persist(T entity);
    <T> Collection<T> persist(T[] entities);
    <T> Collection<T> load(Class<T> entityClass);
    <T> T find(Class<T> entityClass, Serializable id);
    <T> Collection<T> load(String jpql);
    <T> Boolean delete(T entity);
}
