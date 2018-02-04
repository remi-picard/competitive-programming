package com.mbouchenoire.competitive.programming.common.model.gametheory;

/**
 * Represents a cancellable move. It corresponds to one edge of a graph in a game tree.
 *
 * Hint: depending on the game clone complexity/cost, or if you can easily cancel a move,
 * you might be interested in either:
 *
 * 1 - each time a move is executed clone the game state and execute the move.
 *      When the move is canceled return the original game state
 * 2 - execute the move when it is applied, and revert it when canceled
 *
 * @param <G> the game state type impacted by the cancellable move
 */
public interface CancellableMove<G extends Game> extends Move<G> {

    /**
     * Cancels the move
     *
     * @param game the game state
     * @return the (cached or reverted) game state with the move canceled
     */
    G cancel(G game);
}
