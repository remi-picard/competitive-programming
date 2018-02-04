package com.mbouchenoire.competitive.programming.common.model.genetic;

/**
 * Allows to merge two genotypes in order to build a combination of the two genotype's characteristics
 *
 * @param <Genotype> the type representing one candidate
 */
@FunctionalInterface
public interface CandidateMerger<Genotype> {

    /**
     * @param first the first instance of genotype
     * @param second the second instance of genotype
     * @return a new instance that has been created from the first and second genotype
     */
    Genotype merge(Genotype first, Genotype second);
}
