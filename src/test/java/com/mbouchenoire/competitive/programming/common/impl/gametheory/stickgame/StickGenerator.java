package com.mbouchenoire.competitive.programming.common.impl.gametheory.stickgame;

import com.mbouchenoire.competitive.programming.common.model.gametheory.MoveGenerator;

import java.util.ArrayList;
import java.util.List;

public class StickGenerator implements MoveGenerator<StickMove, StickGame> {

    @Override
    public List<StickMove> generateMoves(StickGame game) {
        final List<StickMove> moves = new ArrayList<>();

        if (game.getSticksRemaining() > 2) {
            moves.add(new StickMove(3));
        }

        if (game.getSticksRemaining() > 1) {
            moves.add(new StickMove(2));
        }

        if (game.getSticksRemaining() > 0) {
            moves.add(new StickMove(1));
        }

        return moves;
    }
}
