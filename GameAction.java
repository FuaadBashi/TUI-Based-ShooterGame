package WorkExamples.WorkExamples.GameConsole.src.dev.lpa.game;

import java.util.function.Predicate;

public record GameAction(char key, String prompt, Predicate<Integer> action) {
}
