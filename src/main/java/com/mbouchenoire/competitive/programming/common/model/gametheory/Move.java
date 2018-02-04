package com.mbouchenoire.competitive.programming.common.model.gametheory;

/**
 * Represents a move. It corresponds to one edge of a graph in a game tree.
 *
 * @param <G> the game state type impacted by the move
 */
@FunctionalInterface
public interface Move<G extends Game> {

    /**
     * Executes a move on a game.
     *
     * @param game the game state
     * @return the (new or modified) game state with the move applied
     */
    G execute(G game);
}
