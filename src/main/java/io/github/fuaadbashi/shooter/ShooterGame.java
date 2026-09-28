package io.github.fuaadbashi.shooter;

import io.github.fuaadbashi.game.Game;
import io.github.fuaadbashi.game.GameAction;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * A turn-based shootout. Each turn you search for supplies, shoot at a target or reload, and then
 * the enemy may return fire. Reach {@value #WINNING_SCORE} points before your health runs out.
 */
public class ShooterGame extends Game<Shooter> {

    public static final int WINNING_SCORE = 500;
    static final int HIT_CHANCE_PERCENT = 60;
    static final int ENEMY_HIT_CHANCE_PERCENT = 30;
    static final int POINTS_PER_HIT = 100;
    static final int ENEMY_DAMAGE = 10;

    private final Random random;

    public ShooterGame(String gameName) {
        this(gameName, new Random());
    }

    /** Takes the random source so tests can script every roll. */
    public ShooterGame(String gameName, Random random) {
        super(gameName);
        this.random = random;
    }

    @Override
    public Shooter createNewPlayer(String name) {
        return new Shooter(name);
    }

    public Shooter getShooter(int playerIndex) {
        return getPlayer(playerIndex);
    }

    @Override
    public Map<Character, GameAction> getGameActions(int playerIndex) {
        Map<Character, GameAction> actions = new LinkedHashMap<>();
        addAction(actions, 'F', "Search for supplies", this::findPrize);
        addAction(actions, 'S', "Shoot at the target", this::shoot);
        addAction(actions, 'R', "Reload", this::reload);
        actions.putAll(getStandardActions());
        return actions;
    }

    public boolean findPrize(int playerIndex) {
        Shooter shooter = getPlayer(playerIndex);
        switch (random.nextInt(4)) {
            case 0 -> {
                shooter.addAmmo(3);
                System.out.println("You found a box of ammo (+3 rounds).");
            }
            case 1 -> {
                shooter.heal(20);
                System.out.println("You found a med kit (+20 health).");
            }
            case 2 -> {
                shooter.addScore(50);
                System.out.println("You found a gold coin (+50 points).");
            }
            default -> {
                shooter.takeDamage(15);
                System.out.println("It was a trap! (-15 health)");
            }
        }
        return endTurn(shooter);
    }

    public boolean shoot(int playerIndex) {
        Shooter shooter = getPlayer(playerIndex);
        if (!shooter.fire()) {
            System.out.println("Click. Out of ammo: reload (R) or search for more (F).");
        } else if (random.nextInt(100) < HIT_CHANCE_PERCENT) {
            shooter.addScore(POINTS_PER_HIT);
            System.out.println("Hit! (+" + POINTS_PER_HIT + " points)");
        } else {
            System.out.println("Missed.");
        }
        return endTurn(shooter);
    }

    public boolean reload(int playerIndex) {
        Shooter shooter = getPlayer(playerIndex);
        shooter.reload();
        System.out.println("Reloaded: " + shooter.ammo() + " rounds.");
        return endTurn(shooter);
    }

    /** The enemy's reply, then the win/lose check. Returns true when the game is over. */
    private boolean endTurn(Shooter shooter) {
        if (shooter.score() < WINNING_SCORE && random.nextInt(100) < ENEMY_HIT_CHANCE_PERCENT) {
            shooter.takeDamage(ENEMY_DAMAGE);
            System.out.println("The enemy fires back and hits you (-" + ENEMY_DAMAGE + " health).");
        }
        System.out.println(shooter);

        if (shooter.score() >= WINNING_SCORE) {
            System.out.println("You win with " + shooter.score() + " points!");
            return true;
        }
        if (!shooter.isAlive()) {
            System.out.println("You were taken down. Final score: " + shooter.score());
            return true;
        }
        return false;
    }
}
