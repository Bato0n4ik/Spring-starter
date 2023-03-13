package org.andreev.spring.datsbase.repository;

import java.util.Optional;

public interface CrudRepository <K,V>{

    Optional<V> findById(K id);

    void delete(V entity);
}
