# MinimumSnippet Search Engine Project

## Overview

This project is a simulation of the classic Blackjack card game, implemented in Java. The game includes essential functionalities like dealing cards, assessing hand values, and determining game outcomes, all integrated with a graphical user interface (GUI) for interactive gameplay.

### Key Features

- **Card Deck Management:** Handles shuffling, dealing, and tracking of cards.
- **Player and Dealer Logic:** Implements the rules for player and dealer actions, including hit, stay, and automatic dealer moves.
- **Hand Assessment:** Evaluates and compares the hands of the player and the dealer to determine the winner.
- **Graphical User Interface:** Provides an interactive GUI for placing bets, drawing cards, and displaying game results.

## Project Usage

The Blackjack game allows a player to compete against the dealer (computer) in a standard game of Blackjack. The player can place bets, draw cards, and choose to hit or stay. The dealer automatically follows the game rules to determine when to take additional cards. The game outcome is determined based on the final hand values.

## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- Java IDE such as Eclipse etc.

### Installation

1. **Clone the Repository**

```bash
   git clone https://github.com/arnavaggarwal75/Blackjack-Game.git
```

2. **Open the Project**

Import the project into your preffered IDE as a Java project.

2. **Running the Game**

   - Run the BlackjackGUI class to start the game.
   - Use the GUI to place bets, draw cards, and interact with the game.

### Example Usage:

(images/example.png)

### Project Structure

blackjack/BlackjackModel.java: Contains the core game logic, including deck handling, hand assessment, and game outcome determination.

deckOfCards/: Manages the card deck, including card shuffling, dealing, and representing suits and ranks.

GUI/BlackjackGUI.java: Handles the graphical interface for the game, including user interaction and visual representation of the game state.

tests/Tests.java: Includes JUnit tests to validate the functionality of the game logic.