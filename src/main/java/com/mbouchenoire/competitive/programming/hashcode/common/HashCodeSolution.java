package com.mbouchenoire.competitive.programming.hashcode.common;

import java.util.ArrayList;
import java.util.List;

public class HashCodeSolution {

    private final List<String> output;
    private final int score;

    public HashCodeSolution(List<String> output, int score) {
        this.output = new ArrayList<>(output);
        this.score = score;
    }

    public List<String> getOutput() {
        return output;
    }

    public int getScore() {
        return score;
    }
}
