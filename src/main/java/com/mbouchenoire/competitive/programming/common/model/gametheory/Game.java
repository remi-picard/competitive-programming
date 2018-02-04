package com.mbouchenoire.competitive.programming.common.model.gametheory;

/**
 *	Represents a game state
 */
public interface Game {

    /**
     * The game state must handle the player which is currently playing.
     * Convention: player id represents the index of the player in the evaluated array
     *
     * @return the current player id
     */
    int currentPlayer();

    /**
     * Evaluate the game for each player and scores it. This is a key piece of the IA efficiency.
     * Convention: player id represents the index of the player in the evaluated array
     *
     * @param depth the current depth when exploring the game tree.
     *      the depth is incremented each time a move is executed. Initial game state corresponds to a depth of 0.
     * @return the array of evaluation for each player
     */
    double[] evaluate(int depth);
}
