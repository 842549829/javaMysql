package com.company.classes.irepository;

public interface IAddRepository<T> extends AutoCloseable{
    void add(T entity) throws Exception;
}
