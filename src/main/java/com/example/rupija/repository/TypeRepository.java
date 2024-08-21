package com.example.rupija.repository;

import com.example.rupija.models.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TypeRepository extends CrudRepository<Type, Long> {

    @Query(
            value = "SELECT * FROM tipai WHERE LOWER(tipas) LIKE LOWER(CONCAT('%', :name, '%'))",
            nativeQuery = true
    )
    Iterable<Type> findTypeByName(@Param("name") String name);
}
