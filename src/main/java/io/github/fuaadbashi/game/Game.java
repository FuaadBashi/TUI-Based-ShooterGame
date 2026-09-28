package io.github.fuaadbashi.game;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Base class for a turn-based console game. Subclasses supply the player type and their own
 * actions; every game also gets "print player info" and "quit".
 */
public abstract class Game<T extends Player> {

    private final String gameName;
    private final List<T> players = new ArrayList<>();
    private Map<Character, GameAction> standardActions = null;

    public Game(String gameName) {
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public Map<Character, GameAction> getStandardActions() {
        if (standardActions == null) {
            // LinkedHashMap filled in order: Map.of iterates in an unspecified order, which
            // shuffled the menu between runs.
            standardActions = new LinkedHashMap<>();
            addAction(standardActions, 'I', "Print player info", this::printPlayer);
            addAction(standardActions, 'Q', "Quit game", this::quitGame);
        }
        return standardActions;
    }

    protected static void addAction(
            Map<Character, GameAction> actions,
            char key,
            String prompt,
            Predicate<Integer> action) {
        actions.put(key, new GameAction(key, prompt, action));
    }

    public abstract T createNewPlayer(String name);

    public abstract Map<Character, GameAction> getGameActions(int playerIndex);

    final int addPlayer(String name) {
        T player = createNewPlayer(name);
        if (player != null) {
            players.add(player);
            return players.size() - 1;
        }
        return -1;
    }

    protected final T getPlayer(int playerIndex) {
        return players.get(playerIndex);
    }

    public boolean executeGameAction(int player, GameAction action) {
        return action.action().test(player);
    }

    public boolean printPlayer(int playerIndex) {
        System.out.println(players.get(playerIndex));
        return false;
    }

    public boolean quitGame(int playerIndex) {
        System.out.println("Sorry to see you go, " + players.get(playerIndex).name());
        return true;
    }
}
