# TUI-Based-ShooterGame
ShooterGame is a text-based console game in Java, showcasing object-oriented and functional programming principles. Players can find prizes, use weapons, and perform other actions in an interactive game loop. Built with modular design for easy extension, it features classes like Game, Shooter, and GameConsole.

---

# ShooterGame

The **ShooterGame** is a text-based console game built in Java, where players can perform actions like finding prizes and using weapons. This implementation leverages object-oriented principles and functional programming concepts, featuring classes such as `Game`, `Shooter`, and `GameConsole`.  

## Features  

- Players can interact through game-specific actions such as:
  - **Find Prize**: Simulates finding a prize to adjust scores.
  - **Use Weapon**: Allows players to use a weapon like a pistol.
- Standard game actions, such as viewing player info and quitting the game.
- Modular design supporting easy extension of game mechanics.

## How It Works  

1. **Player Creation**:  
   Players are added to the game using their name. A `Shooter` player is created with attributes and abilities specific to the game.  

2. **Game Actions**:  
   - `Find Prize`: Triggers the player's prize-finding logic.  
   - `Use Weapon`: Simulates shooting a weapon, defaulting to a "pistol".  

3. **Game Loop**:  
   The game runs in a loop, displaying available actions and responding to user inputs until the game ends.

## Code Structure  

- **`ShooterGame`**: Extends the abstract `Game` class to provide game-specific functionality.  
- **`GameAction`**: Encapsulates game actions, including their key bindings and logic.  
- **`Shooter`**: A record representing the player with methods like `findPrize` and `useWeapon`.  
- **`GameConsole`**: Handles user input and displays game actions in a console interface.  

## Getting Started  

### Prerequisites  
- Java 17 or later (for `record` usage).  

### Running the Game  

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/ShooterGame.git
   cd ShooterGame
   ```

2. Compile the code:
   ```bash
   javac dev/lpa/game/*.java
   ```

3. Run the game:
   ```bash
   java dev.lpa.game.GameConsole
   ```

## Extending the Game  

To add new actions or features:  
1. Extend the `Game` class to implement your custom logic.  
2. Create new player types by implementing the `Player` interface.  
3. Add more `GameAction` entries to the game’s action map.  

## Example Code Snippet  

Here’s how actions are mapped in `ShooterGame`:  
```java
public Map<Character, GameAction> getGameActions(int playerIndex) {
    LinkedHashMap<Character, GameAction> map = new LinkedHashMap(Map.of(
        'F', new GameAction('F', "Find Prize", this::findPrize),
        'S', new GameAction('S', "Use your gun", this::useWeapon)
    ));
    map.putAll(this.getStandardActions());
    return map;
}
```

## License  
This project is licensed under the MIT License.

---  
