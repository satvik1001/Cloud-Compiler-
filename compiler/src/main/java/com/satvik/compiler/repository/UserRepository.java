package com.satvik.compiler.repository;

import com.satvik.compiler.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
        extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
