package com.satvik.compiler.controller;

import com.satvik.compiler.entity.Submission;
import com.satvik.compiler.model.CodeRequest;
import com.satvik.compiler.repository.SubmissionRepository;
import com.satvik.compiler.service.CompilerService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CompilerController {

    private final CompilerService compilerService;

    private final SubmissionRepository submissionRepository;

    public CompilerController(
            CompilerService compilerService,
            SubmissionRepository submissionRepository
    ) {

        this.compilerService = compilerService;

        this.submissionRepository =
                submissionRepository;
    }

    @PostMapping("/run")
    public String runCode(
            @RequestBody CodeRequest request
    ) {

        return compilerService.executeCode(

                request.getCode(),

                request.getLanguage(),

                request.getInput()
        );
    }

    @GetMapping("/history")
    public List<Submission> history() {

        return submissionRepository.findAll();
    }
}
