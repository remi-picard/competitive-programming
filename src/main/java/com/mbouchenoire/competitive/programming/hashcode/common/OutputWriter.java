package com.mbouchenoire.competitive.programming.hashcode.common;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OutputWriter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private final HashCodeInput input;

    public OutputWriter(HashCodeInput input) {
        this.input = input;
    }

    public void write(List<String> lines) throws IOException {
        final String homeDirectory = System.getProperty("user.home");

        final String outputFolderPath = String.format("%s/hash-code/%d/%s/output/",
                homeDirectory,
                input.getSession().getYear(),
                input.getSession().getPhase().getName()
        );

        final File outputFolder = new File(outputFolderPath);
        outputFolder.mkdir();

        final String stringTimestamp = DATE_FORMATTER.format(LocalDateTime.now());
        final String outputFileName = stringTimestamp + "__" + input.getName().replace(".in.", ".out.");

        final File outputFile = new File(outputFolderPath, outputFileName);
        outputFile.getParentFile().mkdirs();

        final FileWriter fileWriter = new FileWriter(outputFile);
        final BufferedWriter writer = new BufferedWriter(fileWriter);

        for (String line: lines) {
            writer.write(line);
            writer.newLine();
        }

        writer.close();
    }
}
