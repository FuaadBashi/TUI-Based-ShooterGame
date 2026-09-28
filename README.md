# Shooter Game

[![CI](https://github.com/FuaadBashi/TUI-Based-ShooterGame/actions/workflows/ci.yml/badge.svg)](https://github.com/FuaadBashi/TUI-Based-ShooterGame/actions/workflows/ci.yml)

A turn-based shootout in the terminal, built on a small reusable game framework in Java. Each turn
you search for supplies, shoot at a target or reload, and the enemy may fire back. Reach 500
points before your health runs out.

```
Enter next action: s
-------------------------------------------
Hit! (+100 points)
The enemy fires back and hits you (-10 health).
Fuaad: health 90/100, ammo 5, score 100
```

## Highlights

- **Generic framework, separate from the game.** `Game<T extends Player>` and
  `GameConsole<T extends Game<? extends Player>>` know nothing about shooting. A new game only
  supplies a player type and its actions. The framework adds "player info" and "quit" itself.
- **Actions as data.** Each menu entry is a `GameAction` record holding a key, a label and a
  `Predicate<Integer>`, wired up with method references such as `this::shoot`.
- **Deterministic tests.** The game takes its `Random` as a constructor argument, so tests script
  every roll (hit, miss, enemy fire, what a search finds) and assert exact outcomes.
- **Robust input.** Blank lines, unknown keys and end of input are handled without exceptions.

## Getting started

Requires JDK 17+ and Maven.

```bash
git clone https://github.com/FuaadBashi/TUI-Based-ShooterGame.git
cd TUI-Based-ShooterGame
mvn package
java -jar target/shooter-game.jar
```

## How to play

| Key | Action |
| --- | --- |
| `F` | Search for supplies: ammo, a med kit, gold (+50), or a trap (-15 health) |
| `S` | Shoot: 60% chance of a hit for 100 points; uses one round |
| `R` | Reload to a full clip of 6 |
| `I` | Show health, ammo and score |
| `Q` | Quit |

After every action the enemy has a 30% chance to hit you for 10 health.

## Project structure

```
src/main/java/io/github/fuaadbashi/
├── game/                  reusable framework
│   ├── Game.java          abstract base: players and standard actions
│   ├── GameConsole.java   menu loop and input handling
│   ├── GameAction.java    record: key, label, action
│   └── Player.java
└── shooter/               the shootout itself
    ├── ShooterGame.java   actions, enemy turn, win/lose rules
    ├── Shooter.java       health, ammo, score
    └── Main.java
```

## Tests

```bash
mvn verify
```

This runs the JUnit suite and checks formatting with google-java-format.
