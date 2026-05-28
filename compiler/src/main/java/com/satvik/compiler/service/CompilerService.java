package com.satvik.compiler.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.satvik.compiler.entity.Submission;
import com.satvik.compiler.repository.SubmissionRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

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

        try {

            String pistonLanguage = language;

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

            RestTemplate restTemplate =
                    new RestTemplate();

           HttpURLConnection connection =
        (HttpURLConnection)
        new URL(url).openConnection();

connection.setRequestMethod("POST");

connection.setRequestProperty(
        "Content-Type",
        "application/json"
);

connection.setDoOutput(true);

            HttpHeaders headers =
                    new HttpHeaders();

            headers.setContentType(
                    MediaType.APPLICATION_JSON
            );

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

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(
                            body,
                            headers
                    );

            ResponseEntity<String> response =
                    restTemplate.postForEntity(

                            url,

                            request,

                            String.class
                    );

            ObjectMapper mapper =
                    new ObjectMapper();

            JsonNode root =
                    mapper.readTree(
                            response.getBody()
                    );

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
