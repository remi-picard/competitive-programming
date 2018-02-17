package com.mbouchenoire.competitive.programming.hashcode.common;

import java.util.Scanner;

@FunctionalInterface
public interface HashCodeInputValueParser<T> {

    T parse(Scanner scanner);
}
