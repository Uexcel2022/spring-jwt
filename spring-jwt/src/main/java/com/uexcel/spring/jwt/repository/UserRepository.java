package com.uexcel.spring.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uexcel.spring.jwt.entity.Role;
import com.uexcel.spring.jwt.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    org.springframework.boot.autoconfigure.security.SecurityProperties.User findByEmail(String email);

    User findByRole(Role role);

}
