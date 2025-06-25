# JavaFX Hangman Game 🎮

A JavaFX-based implementation of the classic Hangman game. Includes a GUI, custom word list support, and visual feedback animations. Perfect for practicing Java desktop development.

## ✨ Features

- Interactive JavaFX GUI
- Visual hangman drawing with progressive animation
- Keyboard-like letter input
- Game status messages and restart option
- Custom word list loader (via file path)
- Screenshot previews of game states

## 📸 Screenshots

### Game Start
![Start](src/main/resources/hangman_game/screenshots/initial_screen.png)

### Game Over
![Lose](src/main/resources/hangman_game/screenshots/game_over.png)

### Game Won
![Win](src/main/resources/hangman_game/screenshots/game_won.png)

## 🛠️ How to Run

### Requirements

- Java 21+ (JavaFX 21 tested)
- Maven (or use included wrapper scripts)
- JavaFX SDK (already handled by Maven dependencies)

### Run Instructions

Clone the repository and run using Maven:

```bash
git clone https://github.com/AmirKot/hangman-javafx.git
cd hangman-javafx
./mvnw clean javafx:run
```

Alternatively, open `Main.java` in IntelliJ and run it directly.

## 📂 Custom Word List

You can provide a custom word list in `.txt` format. Place the file in a readable path and type the path in the input box when prompted. Each word should appear on a separate line.

## 🧾 License

This project is licensed under the [MIT License](LICENSE).

---

*Made with ☕ and 💡 by Amir*  
📎 [Connect with me on LinkedIn](https://www.linkedin.com/in/amir-kot-0a7598369)
