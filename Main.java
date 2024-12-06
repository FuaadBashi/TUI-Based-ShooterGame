package WorkExamples.WorkExamples.GameConsole.src.dev.lpa;

import WorkExamples.WorkExamples.GameConsole.src.dev.lpa.game.GameConsole;
import WorkExamples.WorkExamples.GameConsole.src.dev.lpa.game.ShooterGame;

public class Main {

    public static void main(String[] args) {

        var console = new GameConsole<>(new ShooterGame("The Shootout Game"));

        int playerIndex = console.addPlayer();
        console.playGame(playerIndex);
    }
}
