package com.satvik.compiler.service;

import com.satvik.compiler.entity.Submission;
import com.satvik.compiler.repository.SubmissionRepository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

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

    )
    
    public String executeCode(
        String code,
        String language,
        String input
) {

    return "Cloud deployment active 🚀\n\nBackend working successfully.\n\nOnline Docker execution is disabled on Render free plan.";
}

        // CLOUD DEPLOYMENT MESSAGE

      

        long startTime =
                System.currentTimeMillis();

        File folder = null;

        File codeFile = null;

        try {

            // CREATE TEMP FOLDER

            String folderName =
                    "temp/" +
                            System.currentTimeMillis();

            folder =
                    new File(folderName);

            folder.mkdirs();

            // FILE NAME + IMAGE NAME

            String fileName = "";

            String imageName = "";

            if(language.equals("java")) {

                fileName = "Main.java";

                imageName = "java-runner";
            }

            else if(language.equals("python")) {

                fileName = "main.py";

                imageName = "python-runner";
            }

            else if(language.equals("cpp")) {

                fileName = "main.cpp";

                imageName = "cpp-runner";
            }

            // CREATE CODE FILE

            codeFile =
                    new File(folder, fileName);

            FileWriter writer =
                    new FileWriter(codeFile);

            writer.write(code);

            writer.close();

            // DOCKER PROCESS

            ProcessBuilder pb =
                    new ProcessBuilder(

                            "docker",

                            "run",

                            "--rm",

                            "--memory=256m",

                            "--cpus=1",

                            "-v",

                            folder.getAbsolutePath()
                                    + ":/app",

                            imageName
                    );

            Process process =
                    pb.start();

            // INPUT SUPPORT

            OutputStream os =
                    process.getOutputStream();

            os.write(input.getBytes());

            os.flush();

            os.close();

            // TIMEOUT

            boolean finished =
                    process.waitFor(
                            5,
                            TimeUnit.SECONDS
                    );

            if(!finished) {

                process.destroyForcibly();

                return "Time Limit Exceeded";
            }

            // OUTPUT

            BufferedReader reader =
                    new BufferedReader(

                            new InputStreamReader(
                                    process.getInputStream()
                            )
                    );

            BufferedReader errorReader =
                    new BufferedReader(

                            new InputStreamReader(
                                    process.getErrorStream()
                            )
                    );

            StringBuilder output =
                    new StringBuilder();

            String line;

            // NORMAL OUTPUT

            while((line = reader.readLine())
                    != null) {

                output.append(line)
                        .append("\n");
            }

            // ERROR OUTPUT

            while((line = errorReader.readLine())
                    != null) {

                output.append(line)
                        .append("\n");
            }

            long endTime =
                    System.currentTimeMillis();

            // SAVE SUBMISSION

            Submission submission =
                    new Submission();

            submission.setCode(code);

            submission.setLanguage(language);

            submission.setOutput(
                    output.toString()
            );

            submission.setExecutionTime(
                    endTime - startTime
            );

            submission.setUsername(
                    "guest"
            );

            submissionRepository.save(
                    submission
            );

            // FINAL RETURN

            return output.toString();
        }

        catch (Exception e) {

            return e.getMessage();
        }

        finally {

            try {

                // DELETE FILE

                if(codeFile != null) {

                    Files.deleteIfExists(
                            codeFile.toPath()
                    );
                }

                // DELETE FOLDER

                if(folder != null) {

                    Files.deleteIfExists(
                            folder.toPath()
                    );
                }

            }

            catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}
