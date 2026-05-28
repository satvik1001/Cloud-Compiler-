package com.satvik.compiler.repository;

import com.satvik.compiler.entity.Submission;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository
        extends JpaRepository<Submission, Long> {

    List<Submission> findByUsername(
            String username
    );
}
