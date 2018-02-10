package com.mbouchenoire.competitive.programming.hashcode.common;

import com.mbouchenoire.competitive.programming.hashcode.qualification2015.Application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class HashCodeApplication {

    private final HashCodeSession session;

    public HashCodeApplication(HashCodeSession session) {
        this.session = session;
    }

    public void run(HashCodeAlgorithm algorithm) throws IOException {
        final ClassLoader classLoader = Application.class.getClassLoader();
        final String inputFolderPath = "hashcode/" + session.getPhase().getName() + session.getYear() + "/input";
        final URL resource = classLoader.getResource(inputFolderPath);
        final File inputFolder = new File(Objects.requireNonNull(resource).getFile());

        int finalScore = 0;

        for (File inputFile : Objects.requireNonNull(inputFolder.listFiles())) {
            final Scanner scanner = new Scanner(inputFile);
            final HashCodeInput input = new HashCodeInput(session, inputFile.getName(), scanner);
            final HashCodeLogger logger = new HashCodeLogger(input);

            System.out.println();
            logger.info("============== START ==============");
            final HashCodeSolution solution = algorithm.run(input, logger);
            System.out.println();
            logger.info("========== SCORE: " + solution.getScore() + " ==========");

            finalScore += solution.getScore();

            new OutputWriter(input).write(solution.getOutput());
        }

        final String finalScoreLine = "============== FINAL SCORE: " + finalScore + " ==============";

        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < finalScoreLine.length(); i++) {
            sb.append("=");
        }

        System.out.println();
        System.out.println(sb);
        System.out.println(finalScoreLine);
        System.out.println(sb);
    }
}
