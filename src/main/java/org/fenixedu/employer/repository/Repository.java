package org.fenixedu.employer.repository;

public interface Repository<T> {

    /**
     * Must return an identifier to be used as a handler for the T stored, to be
     * used for removal.
     * 
     * @return
     */
    public String store(T obj);

    /**
     * Removes the job from the repository, effectively deleting id.
     * 
     * @param id
     * @return The T removed
     */
    public T remove(String id);
}
