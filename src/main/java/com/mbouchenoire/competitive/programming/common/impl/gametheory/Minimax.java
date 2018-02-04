package com.mbouchenoire.competitive.programming.common.impl.gametheory;

import com.mbouchenoire.competitive.programming.common.model.gametheory.CancellableMove;
import com.mbouchenoire.competitive.programming.common.model.gametheory.Game;
import com.mbouchenoire.competitive.programming.common.model.gametheory.MoveGenerator;
import com.mbouchenoire.competitive.programming.common.model.time.TimeoutException;
import com.mbouchenoire.competitive.programming.common.model.time.Timer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *         Minimax class allows to find the best move a player can do
 *         in a zero sum game considering the other player will be playing his best move at
 *         each iteration.
 *         It includes the alpha beta prunning optimisation in order to explore less branches.
 *         It also stores the current best "killer" move in order to explore the best branches first and enhance the pruning rate
 * @see <a href="https://en.wikipedia.org/wiki/Minimax">Minimax</a> and <a href="https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning">Alpha-beta pruning</a>
 *
 * @param <M>
 *            The class that model a move in the game tree
 * @param <G>
 *            The class that model the Game state
 */
public class Minimax<M extends CancellableMove<G>, G extends Game> {

    private static class AlphaBetaPrunningException extends Exception {
        private static final long serialVersionUID = 4338636523317720681L;
    }

    private class MinMaxEvaluatedMove implements Comparable<MinMaxEvaluatedMove> {
        private final M move;
        private final double value;
        private final MinMaxEvaluatedMove bestSubMove;

        public MinMaxEvaluatedMove(M move, double value, MinMaxEvaluatedMove bestSubMove) {
            this.move = move;
            this.value = value;
            this.bestSubMove = bestSubMove;
        }

        @Override
        public int compareTo(MinMaxEvaluatedMove o) {
            return Double.compare(value, o.value);
        }

        public MinMaxEvaluatedMove getBestSubMove() {
            return bestSubMove;
        }

        public M getMove() {
            return move;
        }

        public double getValue() {
            return value;
        }

        @Override
        public String toString() {
            final StringBuilder str = new StringBuilder("M[" + value + ",[");

            if (move != null) {
                str.append(move.toString()).append(",");
            }

            MinMaxEvaluatedMove subMove = bestSubMove;

            while (subMove != null) {
                if (subMove.move != null) {
                    str.append(subMove.toString()).append(",");
                }

                subMove = subMove.bestSubMove;
            }

            return str + "]";
        }
    }

    private MinMaxEvaluatedMove killer;

    private final Timer timer;

    /**
     * Minimax constructor
     *
     * @param timer
     *            timer instance in order to cancel the search of the best move
     *            if we are running out of time
     */
    public Minimax(Timer timer) {
        this.timer = timer;
    }

    private List<MinMaxEvaluatedMove> evaluateSubPossibilities(G game, MoveGenerator<M, G> generator, int depth, double alpha, double beta, boolean player,
                                                               boolean alphaBetaAtThisLevel, MinMaxEvaluatedMove previousAnalysisBest) throws AlphaBetaPrunningException, TimeoutException {
        final List<MinMaxEvaluatedMove> moves = new LinkedList<>();

        List<M> orderedMoves;
        final List<M> generatedMoves = generator.generateMoves(game);

        // killer first
        if (previousAnalysisBest != null && generatedMoves.contains(previousAnalysisBest.getMove())) {
            orderedMoves = new ArrayList<>();
            final M killerMove = generatedMoves.remove(generatedMoves.indexOf(previousAnalysisBest.getMove()));
            orderedMoves.add(killerMove);
            orderedMoves.addAll(generatedMoves);
        } else {
            orderedMoves = generatedMoves;
        }

        for (final M move : orderedMoves) {
            timer.timeCheck();
            final G movedGame = move.execute(game);
            MinMaxEvaluatedMove child = null;
            try {
                final MinMaxEvaluatedMove bestSubChild = minimax(movedGame, generator, depth - 1, alpha, beta, !player, previousAnalysisBest == null ? null
                        : previousAnalysisBest.getBestSubMove());
                child = new MinMaxEvaluatedMove(move, bestSubChild.getValue(), bestSubChild);
            } catch (final AlphaBetaPrunningException e) {
                move.cancel(game);
            }
            if (child != null) {
                // Alpha beta prunning
                if (alphaBetaAtThisLevel) {
                    if (player) {
                        alpha = Math.max(alpha, child.getValue());
                        if (beta <= alpha) {
                            move.cancel(game);
                            throw new AlphaBetaPrunningException();
                        }
                    } else {
                        beta = Math.min(beta, child.getValue());
                        if (beta <= alpha) {
                            move.cancel(game);
                            throw new AlphaBetaPrunningException();
                        }
                    }
                }
                moves.add(child);
                move.cancel(game);
            }
        }
        return moves;
    }

    private MinMaxEvaluatedMove minimax(G game, MoveGenerator<M, G> generator, int depth, double alpha, double beta, boolean player,
                                        MinMaxEvaluatedMove previousAnalysisBest) throws AlphaBetaPrunningException, TimeoutException {
        if (depth == 0) {
            return new MinMaxEvaluatedMove(null, scoreFromEvaluatedGame(game.evaluate(depth)), null);// Evaluated game status
        }
        final List<MinMaxEvaluatedMove> moves = evaluateSubPossibilities(game, generator, depth, alpha, beta, player, true, previousAnalysisBest);
        if (!moves.isEmpty()) {
            Collections.sort(moves);
            System.err.println("Moves:" + moves);
            return moves.get(player ? (moves.size() - 1) : 0);
        } else {
            return new MinMaxEvaluatedMove(null, scoreFromEvaluatedGame(game.evaluate(depth)), null);// Real end game status
        }
    }

    /**
     * Search in the game tree the best move using minimax with alpha beta pruning
     * Search will start at depthMin and increment up to depthMax until a timeout is reached.
     * Thanks to the previous depth search, it first tries to replay the best found move so far so it
     * take maximum advantage of the pruning
     *
     * @param game
     *            The current state of the game
     * @param generator
     *            The move generator that will generate all the possible move of
     *            the playing player at each turn
     * @param depthStart
     *            the start depth up to which the game tree will be expanded
     * @param depthMax
     *            the maximum depth up to which the game tree will be expanded
     * @return the best move you can play considering the other player is selecting
     *         the best move for him at each turn
     */
    public M best(final G game, final MoveGenerator<M, G> generator, int depthStart, int depthMax) {
        MinMaxEvaluatedMove best = null;
        try {
            for (int depth=depthStart+1; depth<depthMax+1; depth++){
                try {
                    best = minimax(game, generator, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, game.currentPlayer() == 0, killer);
                    killer = best;
                } catch (final AlphaBetaPrunningException e) {
                    // Should never happen
                    throw new RuntimeException("evaluated move found with value not between + infinity and - infinity...");
                }
            }
        } catch (TimeoutException e) {
            //Expected, we just reach a timeout.
        }
        if (best==null){
            return null;
        }
        return best.getMove();
    }

    private double scoreFromEvaluatedGame(double[] scores) {
        return scores[0] - scores[1];
    }
}
