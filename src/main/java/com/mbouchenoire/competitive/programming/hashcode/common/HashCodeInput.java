package com.mbouchenoire.competitive.programming.hashcode.common;

import java.util.Scanner;

public class HashCodeInput {

    private final HashCodeSession session;
    private final String name;
    private final Scanner scanner;

    public HashCodeInput(HashCodeSession session, String name, Scanner scanner) {
        this.session = session;
        this.name = name;
        this.scanner = scanner;
    }

    public HashCodeSession getSession() {
        return session;
    }

    public String getName() {
        return name;
    }

    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public String toString() {
        return name;
    }
}
