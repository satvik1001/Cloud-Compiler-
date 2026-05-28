package com.satvik.compiler.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.satvik.compiler.entity.Submission;
import com.satvik.compiler.repository.SubmissionRepository;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class CompilerService {

    private final SubmissionRepository submissionRepository;

    public CompilerService(
            SubmissionRepository submissionRepository
    ) {

        this.submissionRepository =
                submissionRepository;
    }

    public String executeCode(

            String code,

            String language,

            String input

    ) {

        try {

            String pistonLanguage =
                    language;

            String version = "*";

            if(language.equals("java")) {

                pistonLanguage = "java";

                version = "15.0.2";
            }

            else if(language.equals("python")) {

                pistonLanguage = "python";

                version = "3.10.0";
            }

            else if(language.equals("cpp")) {

                pistonLanguage = "cpp";

                version = "10.2.0";
            }

            Map<String, Object> body =
                    new HashMap<>();

            body.put(
                    "language",
                    pistonLanguage
            );

            body.put(
                    "version",
                    version
            );

            body.put(
                    "stdin",
                    input
            );

            Map<String, String> file =
                    new HashMap<>();

            file.put(
                    "content",
                    code
            );

            body.put(
                    "files",
                    new Map[]{file}
            );

            WebClient client =
                    WebClient.create();

            String response =

                    client.post()

                            .uri(
                                    "https://emkc.org/api/v2/piston/execute"
                            )

                            .contentType(
                                    MediaType.APPLICATION_JSON
                            )

                            .bodyValue(body)

                            .retrieve()

                            .bodyToMono(String.class)

                            .block();

            ObjectMapper mapper =
                    new ObjectMapper();

            JsonNode root =
                    mapper.readTree(response);

            String output =
                    root.path("run")
                            .path("output")
                            .asText();

            Submission submission =
                    new Submission();

            submission.setCode(code);

            submission.setLanguage(language);

            submission.setOutput(output);

            submission.setExecutionTime(0);

            submission.setUsername("satvik");

            submissionRepository.save(
                    submission
            );

            return output;

        }

        catch (Exception e) {

            return e.getMessage();
        }
    }
}
