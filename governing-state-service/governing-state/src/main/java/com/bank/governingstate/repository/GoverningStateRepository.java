package com.bank.governingstate.repository;

import com.bank.governingstate.entity.GoverningStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoverningStateRepository extends JpaRepository<GoverningStateEntity, Long> {

    Optional<GoverningStateEntity> findByZipCode(String zipCode);
}
