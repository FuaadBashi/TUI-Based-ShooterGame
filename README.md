# Shooter Game — Java Console Exercise

A text-based Java game organized around a generic game console, players, actions, and a shooter-game implementation.

## Run locally

Use JDK 17 or later.

```bash
git clone https://github.com/FuaadBashi/TUI-Based-ShooterGame.git
cd TUI-Based-ShooterGame
mkdir -p out
javac -d out *.java
java -cp out WorkExamples.WorkExamples.GameConsole.src.dev.lpa.Main
```

## Code to explore

- [Main.java](Main.java): constructs the console, adds a player, and starts play.
- [GameConsole.java](GameConsole.java): interaction and game coordination.
- [GameAction.java](GameAction.java): action abstraction.
- [ShooterGame.java](ShooterGame.java) and [Shooter.java](Shooter.java): game-specific behavior.

The source files are stored at the root even though they declare packages. The `-d out` compiler option generates the package layout needed to run them.
