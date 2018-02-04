package com.mbouchenoire.competitive.programming.common.model.gametheory;

import java.util.List;

/**
 * Produces the possible moves given a specific game state
 *
 * Hint: It might be worth not generating all the possible moves,
 * but only "interesting" ones so that you can search deeper in the game tree.
 *
 * @param <M> the move type representing the action a player can do
 * @param <G> the game type representing the game state
 */
@FunctionalInterface
public interface MoveGenerator<M extends Move<G>, G extends Game> {

    /**
     * Generate all the moves a player can do from a given game state.
     * If no moves are generated, we consider the game is ended
     * Hint: if a player is dead but the others continue to play, you should either return a neutral move that does not change the game state, either manage it
     * directly in the game state to skip the player once a move is executed
     *
     * @param game the game state from which you must generate the moves
     * @return the list of all the moves you want to be taken into account during the game tree exploration
     */
    List<M> generateMoves(G game);
}
