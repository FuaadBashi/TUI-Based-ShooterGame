package io.github.fuaadbashi.shooter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.fuaadbashi.game.GameConsole;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class ShooterGameTest {

    private static final int HIT = 0;
    private static final int MISS = 99;
    private static final int ENEMY_HITS = 0;
    private static final int ENEMY_MISSES = 99;

    private ShooterGame game;
    private int player;

    /** Starts a game whose Random returns the given rolls in order, then always the highest. */
    private Shooter start(Integer... rolls) {
        Deque<Integer> queue = new ArrayDeque<>(List.of(rolls));
        Random scripted =
                new Random() {
                    @Override
                    public int nextInt(int bound) {
                        return queue.isEmpty() ? bound - 1 : queue.poll();
                    }
                };
        game = new ShooterGame("Test", scripted);
        player = new GameConsole<>(game, new Scanner("Tester\n")).addPlayer();
        return game.getShooter(player);
    }

    @Test
    void aHitScoresPointsAndUsesOneRound() {
        Shooter shooter = start(HIT, ENEMY_MISSES);

        game.shoot(player);

        assertEquals(ShooterGame.POINTS_PER_HIT, shooter.score());
        assertEquals(Shooter.CLIP_SIZE - 1, shooter.ammo());
    }

    @Test
    void theEnemyCanReturnFireAfterYourTurn() {
        Shooter shooter = start(MISS, ENEMY_HITS);

        game.shoot(player);

        assertEquals(0, shooter.score());
        assertEquals(Shooter.MAX_HEALTH - ShooterGame.ENEMY_DAMAGE, shooter.health());
    }

    @Test
    void anEmptyGunCannotFireUntilReloaded() {
        Shooter shooter = start(); // every roll misses
        for (int i = 0; i < Shooter.CLIP_SIZE + 1; i++) {
            game.shoot(player);
        }
        assertEquals(0, shooter.ammo());

        game.reload(player);

        assertEquals(Shooter.CLIP_SIZE, shooter.ammo());
    }

    @Test
    void reachingTheWinningScoreEndsTheGame() {
        Shooter shooter =
                start(
                        HIT,
                        ENEMY_MISSES,
                        HIT,
                        ENEMY_MISSES,
                        HIT,
                        ENEMY_MISSES,
                        HIT,
                        ENEMY_MISSES,
                        HIT);

        boolean over = false;
        for (int i = 0; i < 5; i++) {
            assertFalse(over, "the game ended early");
            over = game.shoot(player);
        }

        assertTrue(over);
        assertEquals(ShooterGame.WINNING_SCORE, shooter.score());
    }

    @Test
    void theGameEndsWhenHealthRunsOut() {
        Shooter shooter = start(MISS, ENEMY_HITS);
        shooter.takeDamage(Shooter.MAX_HEALTH - ShooterGame.ENEMY_DAMAGE);

        boolean over = game.shoot(player);

        assertTrue(over);
        assertFalse(shooter.isAlive());
    }

    @Test
    void aMedKitNeverHealsAboveMaximumHealth() {
        Shooter shooter = start(1, ENEMY_MISSES); // 1 = med kit

        game.findPrize(player);

        assertEquals(Shooter.MAX_HEALTH, shooter.health());
    }

    @Test
    void theConsoleIgnoresBlankLinesAndStopsAtEndOfInput() {
        game = new ShooterGame("Test", new Random(1));
        var console = new GameConsole<>(game, new Scanner("Tester\n\n\nx\n"));

        console.playGame(console.addPlayer());
        // Reaching here without an exception is the claim.
    }
}
