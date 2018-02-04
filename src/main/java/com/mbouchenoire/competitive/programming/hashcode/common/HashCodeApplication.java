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

        for (File inputFile : Objects.requireNonNull(inputFolder.listFiles())) {
            final Scanner scanner = new Scanner(inputFile);
            final HashCodeInput input = new HashCodeInput(session, inputFile.getName(), scanner);
            final List<String> outputLines = algorithm.run(input);
            new OutputWriter(input).write(outputLines);
        }
    }
}
