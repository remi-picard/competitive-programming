package com.mbouchenoire.competitive.programming.common.impl.genetic;

import org.junit.Test;

import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class GeneticAlgorithmTest {

    private static class Combination {

        public static Combination newInstance() {
            generatorValue = (generatorValue + 1) % 9;
            return new Combination(generatorValue, generatorValue, generatorValue, generatorValue);
        }

        private static int generatorValue = 0;

        private final int first;
        private final int second;
        private final int third;
        private final int fourth;

        public Combination(int first, int second, int third, int fourth) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.fourth = fourth;
        }

        public double evaluate(Combination toBeFound) {
            double result = 0;

            if (first == toBeFound.first) {
                result += 10 + first;
            }

            if (second == toBeFound.second) {
                result += 10 + second;
            }

            if (third == toBeFound.third) {
                result += 10 + third;
            }

            if (fourth == toBeFound.fourth) {
                result += 10 + fourth;
            }

            return result;
        }

        public Combination merge(Combination other) {
            return new Combination(randomBoolean() ? first : other.first, randomBoolean() ? second : other.second, randomBoolean() ? third : other.third,
                    randomBoolean() ? fourth : other.fourth);
        }

        public Combination mutate() {
            return new Combination(first, second, third, fourth + 1);
        }

        private boolean randomBoolean() {
            return random.nextBoolean();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + first;
            result = prime * result + fourth;
            result = prime * result + second;
            result = prime * result + third;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            final Combination other = (Combination) obj;

            if (first != other.first) {
                return false;
            }

            if (fourth != other.fourth) {
                return false;
            }

            if (second != other.second) {
                return false;
            }

            if (third != other.third) {
                return false;
            }

            return true;
        }

        @Override
        public String toString() {
            return "C[" + first + "" + second + "" + third + "" + fourth + "]";
        }
    }

    private static final Random random = new Random(0);


    /**
     * Random stuff is hard to test:
     * Combination could be only found with combination (generator generates only identical values) and mutations (9 could not be reached)
     */
    @Test
    public void testCombinationGuesser() {
        final Combination toBeFound = new Combination(0, 3, 7, 9);
        final GeneticAlgorithm<Combination> algo = new GeneticAlgorithm<>(c -> c.evaluate(toBeFound), () -> Combination.newInstance(), (first,
                                                                                                                                                   second) -> first.merge(second), c -> c.mutate());
        algo.setShuffler(list -> {
            Collections.shuffle(list, random);
        });

        algo.initialize(9);
        algo.iterate(10, 5, 20, 20, 20);

        assertEquals(toBeFound, algo.best());
    }
}
