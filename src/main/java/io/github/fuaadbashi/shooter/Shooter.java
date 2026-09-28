package io.github.fuaadbashi.shooter;

import io.github.fuaadbashi.game.Player;

/** The player's state in the shootout. */
public final class Shooter implements Player {

    public static final int MAX_HEALTH = 100;
    public static final int CLIP_SIZE = 6;

    private final String name;
    private int health = MAX_HEALTH;
    private int ammo = CLIP_SIZE;
    private int score = 0;

    public Shooter(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    public int health() {
        return health;
    }

    public int ammo() {
        return ammo;
    }

    public int score() {
        return score;
    }

    public boolean isAlive() {
        return health > 0;
    }

    void heal(int amount) {
        health = Math.min(MAX_HEALTH, health + amount);
    }

    void takeDamage(int amount) {
        health = Math.max(0, health - amount);
    }

    void addScore(int points) {
        score += points;
    }

    void addAmmo(int rounds) {
        ammo += rounds;
    }

    /** Spends one round. Returns false, spending nothing, when the gun is empty. */
    boolean fire() {
        if (ammo == 0) {
            return false;
        }
        ammo--;
        return true;
    }

    void reload() {
        ammo = Math.max(ammo, CLIP_SIZE);
    }

    @Override
    public String toString() {
        return "%s: health %d/%d, ammo %d, score %d"
                .formatted(name, health, MAX_HEALTH, ammo, score);
    }
}
