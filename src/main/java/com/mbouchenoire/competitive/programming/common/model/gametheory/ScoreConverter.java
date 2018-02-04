package com.mbouchenoire.competitive.programming.common.model.gametheory;

/**
 * Evaluates the relative value of a game state for a player.
 * This value will be used to determine the best move the player will select in the Max-N tree
 * This is particularly useful if you want to consider that the player is trying to maximize only its score, or its score minus the others scores or...
 */
@FunctionalInterface
public interface ScoreConverter {

    double convert(double[] rawScores, int player);
}
