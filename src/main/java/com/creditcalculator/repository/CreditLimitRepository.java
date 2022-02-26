package com.creditcalculator.repository;

import com.creditcalculator.models.CreditLimit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CreditLimitRepository extends CrudRepository<CreditLimit, UUID> {

    @Query("SELECT c FROM CreditLimit c WHERE c.username = :username")
    List<CreditLimit> findAllByUsername(@Param("username") String username);

    List<CreditLimit> findByUsername(String username);
}
