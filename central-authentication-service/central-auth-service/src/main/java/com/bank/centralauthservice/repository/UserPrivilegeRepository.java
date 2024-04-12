package com.bank.centralauthservice.repository;

import com.bank.centralauthservice.entity.UserPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPrivilegeRepository extends JpaRepository<UserPrivilege, Long> {

    List<UserPrivilege> findByUserId(Long userId);
}
