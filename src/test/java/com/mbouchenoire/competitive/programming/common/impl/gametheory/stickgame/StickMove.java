package com.mbouchenoire.competitive.programming.common.impl.gametheory.stickgame;

import com.mbouchenoire.competitive.programming.common.model.gametheory.CancellableMove;

public class StickMove implements CancellableMove<StickGame> {

    private final int sticks;
    private StickGame previousGame;

    public StickMove(int sticks) {
        this.sticks = sticks;
    }

    public int getSticks() {
        return sticks;
    }

    @Override
    public StickGame cancel(StickGame game) {
        if (game.isGameStateDuplication()) {
            return previousGame;
        }

        game.changePlayer();
        game.setSticksRemaining(game.getSticksRemaining() + getSticks());

        log(game, "cancel ");

        return game;
    }

    @Override
    public StickGame execute(StickGame game) {
        final int sticksRemaining = game.getSticksRemaining() - getSticks();

        if (game.isGameStateDuplication()) {
            previousGame = game;
            return new StickGame(1 - game.currentPlayer(), sticksRemaining, true);
        }

        game.setSticksRemaining(sticksRemaining);

        log(game, "execute ");

        game.changePlayer();

        return game;
    }



    private void log(StickGame game, String action) {
        System.out.println("player " + game.currentPlayer() + " " + action + "move " + sticks + " : sticks remaining= " + game.getSticksRemaining());
    }

    @Override
    public String toString() {
        return "Move[" + sticks + "]";
    }
}
