package com.mbouchenoire.competitive.programming.common.model.genetic;

/**
 * Generate a new candidate (randomly or not) during the genetic algorithm
 *
 * @param <Genotype> the type representing one candidate
 */
@FunctionalInterface
public interface CandidateGenerator<Genotype> {

    /**
     * @return a new instance of Genotype that has been generated (randomly or not)
     */
    Genotype generate();
}
