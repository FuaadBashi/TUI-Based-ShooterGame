package io.github.fuaadbashi.game;

import java.util.Map;
import java.util.Scanner;

/** Runs any {@link Game} in the terminal: shows its actions and dispatches the player's choice. */
public class GameConsole<T extends Game<? extends Player>> {

    private final T game;
    private final Scanner scanner;

    public GameConsole(T game) {
        this(game, new Scanner(System.in));
    }

    public GameConsole(T game, Scanner scanner) {
        this.game = game;
        this.scanner = scanner;
    }

    public int addPlayer() {
        System.out.print("Enter your playing name: ");
        String name = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
        if (name.isEmpty()) {
            name = "Player 1";
        }
        // println, not printf: a name containing '%' was treated as a format specifier.
        System.out.println("Welcome to " + game.getGameName() + ", " + name + "!");
        return game.addPlayer(name);
    }

    public void playGame(int playerIndex) {
        boolean done = false;
        while (!done) {
            Map<Character, GameAction> gameActions = game.getGameActions(playerIndex);
            System.out.println("Select from one of the following actions:");
            for (GameAction action : gameActions.values()) {
                System.out.println("\t" + action.prompt() + " (" + action.key() + ")");
            }
            System.out.print("Enter next action: ");

            if (!scanner.hasNextLine()) {
                System.out.println();
                return;
            }
            String input = scanner.nextLine().trim().toUpperCase();
            // An empty line used to throw StringIndexOutOfBoundsException from charAt(0).
            GameAction gameAction = input.isEmpty() ? null : gameActions.get(input.charAt(0));

            if (gameAction == null) {
                System.out.println("Unknown action.");
                continue;
            }
            System.out.println("-------------------------------------------");
            done = game.executeGameAction(playerIndex, gameAction);
            if (!done) {
                System.out.println("-------------------------------------------");
            }
        }
    }
}
