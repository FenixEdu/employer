package org.fenixedu.employer.repository;

public class MockRepository<T> implements Repository<T> {

    @Override
    public String store(T obj) {
        return null;
    }

    @Override
    public T remove(String id) {
        return null;
    }

}
