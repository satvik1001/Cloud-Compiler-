package com.satvik.compiler.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "submission")
public class Submission {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String code;

    private String language;

    @Column(columnDefinition = "LONGTEXT")
    private String output;

    private Long executionTime;

    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(
            Long executionTime
    ) {
        this.executionTime = executionTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(
            String username
    ) {
        this.username = username;
    }
}
