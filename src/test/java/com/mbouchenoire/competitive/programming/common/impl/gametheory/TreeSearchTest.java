package com.mbouchenoire.competitive.programming.common.impl.gametheory;

import com.mbouchenoire.competitive.programming.common.impl.gametheory.stickgame.StickGame;
import com.mbouchenoire.competitive.programming.common.impl.gametheory.stickgame.StickMove;
import com.mbouchenoire.competitive.programming.common.model.gametheory.Game;
import com.mbouchenoire.competitive.programming.common.model.gametheory.Move;
import com.mbouchenoire.competitive.programming.common.model.gametheory.MoveGenerator;
import com.mbouchenoire.competitive.programming.common.model.time.Timer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class TreeSearchTest {

    @Test
    public void testStickGame() {
        final Timer timer = new Timer();
        final TreeSearch<StickMove, StickGame> treeSearch
                = new TreeSearch<>(timer, 0.5, (rawScores, player) -> rawScores[player]);
        Tester.testAlgo((game, generator, maxdepth) -> treeSearch.best(game, generator), true);
    }

    static class NegValueGame implements Game {
        private final int score;
        private final int depth;

        public NegValueGame(int score, int depth) {
            this.score = score;
            this.depth = depth;
        }

        @Override
        public int currentPlayer() {
            return 0;
        }

        @Override
        public double[] evaluate(int depth) {
            if (depth>=5){
                return new double []{-10};
            }

            return new double[]{score};
        }
    }

    static class NegValueMove implements Move<NegValueGame> {
        private static int counter = 2;

        @Override
        public NegValueGame execute(NegValueGame game) {
            return new NegValueGame(counter++, game.depth+1);
        }

        @Override
        public String toString() {
            return "M";
        }

        static void reset(){
            counter = 2;
        }
    }

    @Test
    public void testExpansionResetValue(){
        NegValueMove.reset();

        final TreeSearch<NegValueMove, NegValueGame> treeSearch
                = new TreeSearch<>(new Timer(), 0.9, (s, p) -> s[p]);

        treeSearch.best(new NegValueGame(1,0), game -> {
            final List<NegValueMove> moves = new ArrayList<>();

            if (game.depth<5){
                moves.add(new NegValueMove());
                moves.add(new NegValueMove());
            }

            return moves;
        });

        assertEquals(-10.0, treeSearch.bestEval()[0], 0.001);
    }

    @Test
    public void testPrunning(){
        NegValueMove.reset();

        final TreeSearch<NegValueMove, NegValueGame> treeSearch = new TreeSearch<>(new Timer(), 0.1, (s,p)->s[p]);

        treeSearch.setEvaluationsMax(20);

        final NegValueGame game = new NegValueGame(1,0);

        MoveGenerator<NegValueMove, NegValueGame> generator = game1 -> {
            final List<NegValueMove> moves = new ArrayList<>();

            if (game1.depth<5) {
                moves.add(new NegValueMove());
                moves.add(new NegValueMove());
                moves.add(new NegValueMove());
                moves.add(new NegValueMove());
                moves.add(new NegValueMove());
            }

            return moves;
        };

        NegValueMove best = treeSearch.best(game, generator);

        assertEquals(21.0, treeSearch.bestEval()[0], 0.001);

        treeSearch.prun(best, generator);
        Queue<TreeSearch.TreeSearchNode<NegValueMove, NegValueGame>> nodes = treeSearch.getToBeExpanded();

        for (TreeSearch.TreeSearchNode<NegValueMove, NegValueGame> node : nodes){
            assertTrue(node.getEvaluation()[0]>=17);
        }

        treeSearch.continueBest(generator);

        assertEquals(42, treeSearch.bestEval()[0], 0.001);
    }
}
