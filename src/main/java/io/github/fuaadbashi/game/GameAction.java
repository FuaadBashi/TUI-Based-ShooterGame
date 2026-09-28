package io.github.fuaadbashi.game;

import java.util.function.Predicate;

/**
 * A menu entry: the key that selects it, its label, and what it does.
 *
 * @param action takes the player index and returns true when the game is over
 */
public record GameAction(char key, String prompt, Predicate<Integer> action) {}
