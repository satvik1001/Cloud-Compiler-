package com.satvik.compiler.service;

import com.satvik.compiler.repository.SubmissionRepository;
import org.springframework.stereotype.Service;

@Service
public class CompilerService {

    private final SubmissionRepository submissionRepository;

    public CompilerService(
            SubmissionRepository submissionRepository
    ) {
        this.submissionRepository = submissionRepository;
    }

    public String executeCode(
            String code,
            String language,
            String input
    ) {

        return "Cloud deployment active 🚀\n\nBackend working successfully.\n\nOnline Docker execution is disabled on Render free plan.";
    }
}
