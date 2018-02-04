package com.mbouchenoire.competitive.programming.common.model.genetic;

/**
 * Allows to create a mutated instance of a genotype
 *
 * @param <Genotype> the type representing one candidate
 */
@FunctionalInterface
public interface CandidateMutator<Genotype> {

    /**
     * @param candidate the source instance that will be mutated
     * @return a new instance that has been created from the candidate and modified
     */
    Genotype mutate(Genotype candidate);
}
