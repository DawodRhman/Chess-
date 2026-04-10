<div align="center">

# ♟️ Chess Game — by Dawood Technologies

**A beautiful, two-player chess game built with Java & JavaFX, powered by a matrix-based board engine and custom move-validation algorithms.**

[![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=java)](https://www.java.com/)
[![JavaFX](https://img.shields.io/badge/JavaFX-17%2B-blue?logo=openjfx)](https://openjfx.io/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Active-brightgreen)]()

</div>

---

## 📖 Table of Contents

1. [Project Overview](#-project-overview)
2. [Features](#-features)
3. [Technologies Used](#️-technologies-used)
4. [System Architecture](#️-system-architecture)
5. [Algorithm Details](#-algorithm-details)
6. [Project Structure](#-project-structure)
7. [Installation & Setup](#-installation--setup)
8. [Building & Running](#-building--running)
9. [How to Play](#-how-to-play)
10. [Customization](#-customization)
11. [Game Features Deep Dive](#-game-features-deep-dive)
12. [Future Enhancements](#-future-enhancements)
13. [Contributing](#-contributing)
14. [References](#-references)

---

## 🎯 Project Overview

This is a fully functional **two-player chess game** built entirely in **Java** using the **JavaFX** framework for a rich, graphical user interface. The game engine is built from scratch — no chess libraries — making it a great showcase of algorithmic thinking and object-oriented design.

The board is modeled as an **8×8 two-dimensional matrix**, which makes piece placement, lookup, and move validation both intuitive and highly efficient. Every piece follows the official chess rules, and the game enforces turn-based play, legal move validation, and path obstruction checks in real time.

> 💡 This project was built as a learning journey — from day one, figuring out JavaFX event handlers, to implementing diagonal pawn captures and path-blocking logic. The commit comments tell the story!

---

## ✨ Features

| Feature | Description |
|---|---|
| 🎨 **Beautiful Board** | Classic chess colour scheme — cream `#f0d9b5` and brown `#b58863` |
| 🖱️ **Drag & Drop** | Intuitive drag-and-drop piece movement with smooth mouse tracking |
| ♟️ **All 6 Piece Types** | Rook, Knight, Bishop, Queen, King, Pawn — all with correct movement rules |
| 🔄 **Turn Enforcement** | Strict alternating turns — White goes first, then Black |
| 🚧 **Path Obstruction** | Sliding pieces (Rook, Bishop, Queen) cannot jump over other pieces |
| ⚔️ **Capture Mechanics** | Full piece capture with automatic board update and removal |
| 🐴 **Knight Jump** | Knights correctly jump over all pieces in their L-shaped path |
| 🐾 **Pawn Double Move** | Pawns may advance two squares from their starting row |
| ↗️ **Pawn Diagonal Capture** | Pawns capture diagonally, as per official rules |
| 🖼️ **Custom Piece Images** | Plug in your own PNG images for any or all pieces |
| ⚠️ **Invalid Move Alert** | Animated pop-up image alert when a player tries to move out of turn |

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| **Java 17+** | Core application language |
| **JavaFX 17+** | GUI framework — scene graph, event handling, animations |
| **2D Array (Matrix)** | Board state representation |
| **OOP (Classes & Encapsulation)** | `Piece` and `Main` class design |
| **JavaFX Timeline / KeyFrame** | Auto-closing alert animation |
| **ImageView / Image** | Rendering chess piece images |
| **MouseEvent API** | Drag-and-drop interaction |

---

## 🏗️ System Architecture

### Board Representation — The Matrix

The entire chessboard state lives in a single 2D array of `Piece` objects:

```java
private Piece[][] pieces = new Piece[8][8];
```

Each cell either holds a `Piece` object (occupied square) or `null` (empty square). This gives O(1) piece lookup by position — no list searching needed.

```
Row 0 → White's back rank  [R][N][B][Q][K][B][N][R]
Row 1 → White's pawns      [P][P][P][P][P][P][P][P]
Row 2 → Empty              [ ][ ][ ][ ][ ][ ][ ][ ]
...
Row 6 → Black's pawns      [p][p][p][p][p][p][p][p]
Row 7 → Black's back rank  [r][n][b][q][k][b][n][r]
```

### Board Rendering

The visual board is drawn using JavaFX `Rectangle` objects, laid out in a nested loop:

```java
for (int row = 0; row < BOARD_SIZE; row++) {
    for (int col = 0; col < BOARD_SIZE; col++) {
        Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
        square.setX(col * SQUARE_SIZE);
        square.setY(row * SQUARE_SIZE);
        // Checkerboard pattern: light if (row+col) is even
        square.setFill((row + col) % 2 == 0
            ? Color.web(WHITE_SQUARE_COLOR)   // "#f0d9b5"
            : Color.web(BLACK_SQUARE_COLOR)); // "#b58863"
        root.getChildren().add(square);
    }
}
```

### Coordinate Mapping

| Concept | Value |
|---|---|
| Board squares | 8 × 8 |
| Square size | 120 × 120 px |
| Piece image size | 125 × 125 px |
| Total window | 960 × 960 px |

Pixel → grid conversion used when a piece is dropped:

```java
int newRow = (int) (event.getY() / SQUARE_SIZE);
int newCol = (int) (event.getX() / SQUARE_SIZE);
```

---

## 🔬 Algorithm Details

### Move Validation

Each piece's movement is validated in `Piece.isValidMove()`. The method dispatches to a piece-specific validator based on the piece's `type` string:

```java
public boolean isValidMove(int newRow, int newCol, Piece targetPiece) {
    // Cannot move to the same square
    if (row == newRow && col == newCol) return false;

    // Cannot capture your own piece
    if (targetPiece != null && targetPiece.isWhite() == isWhite) return false;

    switch (type) {
        case "rooks":   return isValidRookMove(newRow, newCol);
        case "knights": return isValidKnightMove(newRow, newCol);
        case "bishops": return isValidBishopMove(newRow, newCol);
        case "queen":   return isValidQueenMove(newRow, newCol);
        case "king":    return isValidKingMove(newRow, newCol);
        case "pawns":   return isValidPawnMove(newRow, newCol, targetPiece);
        default:        return false;
    }
}
```

### Per-Piece Rules

| Piece | Logic |
|---|---|
| **Rook** | `newRow == row` OR `newCol == col` — straight lines only |
| **Knight** | `(rowDiff==2 && colDiff==1)` OR `(rowDiff==1 && colDiff==2)` — L-shape |
| **Bishop** | `rowDiff == colDiff` — diagonals only |
| **Queen** | Rook logic OR Bishop logic combined |
| **King** | `rowDiff <= 1 && colDiff <= 1` — one step in any direction |
| **Pawn** | Direction-aware: forward only, diagonal capture, double first move |

### Path Obstruction (`uperSe`)

Sliding pieces — Rook, Bishop, Queen — are checked for path blockage using the `uperSe` method. It steps through intermediate squares between source and destination and returns `true` if any are occupied:

> 📝 **Note:** `uperSe` is the method name as written in the source. A future refactor could rename it to `isPathBlocked()` or `hasObstruction()` for better readability.

```java
private boolean uperSe(int currentRow, int currentCol, int newRow, int newCol) {
    // Knights jump — no obstruction check needed
    if (pieces[currentRow][currentCol].getType().equals("knights")) return false;

    int rowStep = Integer.signum(newRow - currentRow);
    int colStep = Integer.signum(newCol - currentCol);

    int row = currentRow + rowStep;
    int col = currentCol + colStep;

    while (row != newRow || col != newCol) {
        if (pieces[row][col] != null) return true; // Path is blocked
        row += rowStep;
        col += colStep;
    }
    return false; // Path is clear
}
```

### Turn Management

Turns are tracked with a single `int count` variable (starting at `2`). White moves on odd counts, Black on even counts:

```java
boolean whitesTurn = (count % 2 == 1);
boolean blacksTurn = (count % 2 == 0);

// White piece + white's turn OR Black piece + black's turn
if ((piece.isWhite() && whitesTurn) || (!piece.isWhite() && blacksTurn)) {
    // allow the move
}
```

After every valid move, `count` is incremented.

### Absolute Value Helper

A custom `topostivity()` utility converts any integer to its absolute value — used throughout move validation:

```java
public static int topostivity(int a) {
    return (a < 0) ? a * -1 : a;
}
```

> 📝 **Note:** `topostivity` is the current method name in the source code. A future refactor could rename it to `absoluteValue` or simply use `Math.abs()` for clarity.

---

## 📁 Project Structure

```
Chess-/
├── README.md
└── chess/
    └── src/
        └── application/
            ├── Main.java                        # Application entry point, board & event logic
            ├── Piece.java                       # Piece model + move validation algorithms
            ├── application.css                  # JavaFX stylesheet
            ├── chess-rook-white.png             # White rook image
            ├── chess-rook-black.png             # Black rook image
            ├── chess-knight-white.png           # White knight image
            ├── chess-knight-black.png           # Black knight image
            ├── chess-bishop-white.png           # White bishop image
            ├── chess-bishop-black.png           # Black bishop image
            ├── chess-queen-white.png            # White queen image
            ├── chess-queen-black.png            # Black queen image
            ├── chess-king-white.png             # White king image
            ├── chess-king-black.png             # Black king image
            ├── chess-pawn-white.png             # White pawn image
            ├── chess-pawn-black.png             # Black pawn image
            └── New_Project-removebg-preview.png # Invalid-move alert image
```

### Key Classes

#### `Main.java` — The Game Engine
| Method | Responsibility |
|---|---|
| `start()` | Creates JavaFX scene, initialises board and pieces |
| `initializeBoard()` | Draws 64 coloured rectangles |
| `initializePieces()` | Populates the `Piece[][]` matrix and renders images |
| `handleMousePressed()` | Selects a piece and records drag origin |
| `handleMouseDragged()` | Moves piece image with the mouse cursor |
| `handleMouseReleased()` | Calculates target cell, calls `movePiece()` |
| `movePiece()` | Validates move, enforces turn, updates matrix and UI |
| `uperSe()` | Checks for path obstruction for sliding pieces |
| `toOrginal()` | Snaps a piece back to its grid-aligned position |
| `removePiece()` | Removes captured piece from matrix and scene |
| `findPiece()` | Finds the `Piece` object matching a given `ImageView` |

#### `Piece.java` — The Piece Model
| Method | Responsibility |
|---|---|
| `isValidMove()` | Dispatches to per-piece validation |
| `isValidRookMove()` | Rook horizontal/vertical logic |
| `isValidKnightMove()` | Knight L-shape logic |
| `isValidBishopMove()` | Bishop diagonal logic |
| `isValidQueenMove()` | Queen combined logic |
| `isValidKingMove()` | King one-step logic |
| `isValidPawnMove()` | Pawn forward, double-step, diagonal capture logic |
| `topostivity()` | Absolute value helper |

---

## 📦 Installation & Setup

### Prerequisites

| Requirement | Version |
|---|---|
| Java Development Kit (JDK) | 17 or higher |
| JavaFX SDK | 17 or higher |
| IDE (optional) | Eclipse, IntelliJ IDEA, or VS Code |

### 1. Install Java

Download and install the latest JDK from [Adoptium](https://adoptium.net/) or [Oracle](https://www.oracle.com/java/technologies/downloads/).

Verify your installation:
```bash
java -version
# Expected: java version "17.x.x" or higher
```

### 2. Download JavaFX SDK

Download the JavaFX SDK that matches your JDK version from [openjfx.io](https://openjfx.io/).

Extract it to a folder, e.g.:
- **Windows**: `C:\javafx-sdk-17\`
- **macOS / Linux**: `~/javafx-sdk-17/`

### 3. Clone this Repository

```bash
git clone https://github.com/DawodRhman/Chess-.git
cd Chess-
```

### 4. Update Image Paths ⚠️

The piece images are loaded with absolute file paths. You **must** update these paths to match your local setup before the game will display correctly.

Open `chess/src/application/Main.java` and replace all occurrences of:

```
file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/
```

with your own absolute path, for example:

```
file:///C:/Users/YourName/projects/Chess-/chess/src/application/
```

On **macOS/Linux**, use the format:
```
file:///home/yourname/projects/Chess-/chess/src/application/
```

---

## 🚀 Building & Running

### Option A: Eclipse IDE

1. Open Eclipse → **File → Import → Existing Projects into Workspace**
2. Select the `chess/` folder
3. Right-click the project → **Build Path → Configure Build Path**
4. Add the JavaFX SDK `lib/` folder under **Libraries → Add External JARs**
5. Edit the run configuration:
   - Go to **Run → Run Configurations → Java Application**
   - Under **Arguments → VM Arguments**, add:
     ```
     --module-path "C:\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml
     ```
6. Run `Main.java`

### Option B: Command Line

```bash
# Set your paths
JAVAFX_LIB=/path/to/javafx-sdk-17/lib
SRC=chess/src

# Compile
javac --module-path "$JAVAFX_LIB" \
      --add-modules javafx.controls \
      -d out \
      $SRC/module-info.java \
      $SRC/application/Main.java \
      $SRC/application/Piece.java

# Run
java --module-path "$JAVAFX_LIB" \
     --add-modules javafx.controls \
     -cp out \
     application.Main
```

### Option C: IntelliJ IDEA

1. Open the `chess/` folder as a project
2. Go to **File → Project Structure → Libraries** → add the JavaFX SDK `lib/` directory
3. Go to **Run → Edit Configurations** → add VM options:
   ```
   --module-path /path/to/javafx-sdk-17/lib --add-modules javafx.controls
   ```
4. Run `Main`

---

## 🎮 How to Play

### Starting the Game

When the application launches, you will see the chess board with all 32 pieces in their starting positions. **White always moves first.**

### Making a Move

1. **Click and hold** on one of your pieces
2. **Drag** the piece to the target square
3. **Release** the mouse button to place the piece

The game engine will automatically:
- Validate the move against the piece's movement rules
- Check that the path is clear (for Rooks, Bishops, and Queens)
- Capture any opponent piece on the target square
- Switch the turn to the other player

### Invalid Moves

If you attempt to move a piece out of turn, a **pop-up alert** will appear briefly with a custom image, then automatically close after 1 second.

If a move is geometrically illegal (e.g., a Rook trying to move diagonally), the piece will snap back to its original square.

### Controls Summary

| Action | How |
|---|---|
| Select piece | Left-click and hold |
| Move piece | Drag to destination |
| Place piece | Release mouse button |
| Capture opponent | Drop piece onto opponent's square |

### Piece Movement Reference

| Piece | Movement |
|---|---|
| ♖ **Rook** | Any number of squares horizontally or vertically |
| ♘ **Knight** | L-shape: 2 squares in one direction + 1 square perpendicular (can jump over pieces) |
| ♗ **Bishop** | Any number of squares diagonally |
| ♕ **Queen** | Any number of squares in any direction (horizontal, vertical, diagonal) |
| ♔ **King** | One square in any direction |
| ♙ **Pawn** | One square forward; two squares from starting row; captures diagonally |

---

## 🎨 Customization

### Changing Piece Images

Each piece image is loaded from an absolute file path in `Main.java`. To use your own images:

1. Place your PNG files (recommended size: ~125×125 px) in the `chess/src/application/` folder
2. In `Main.java`, update each image path. For example, to change the white rook:

```java
// Before
pieces[i][j] = new Piece(i, j, true, "rooks",
    "file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/chess-rook-white.png");

// After — using your custom image
pieces[i][j] = new Piece(i, j, true, "rooks",
    "file:///C:/Users/YourName/projects/Chess-/chess/src/application/my-rook.png");
```

> 💡 **Tip:** To make paths portable, consider using relative paths with `getClass().getResource()`:
> ```java
> getClass().getResource("chess-rook-white.png").toExternalForm()
> ```

### Changing Board Colors

The board colors are defined as constants at the top of `Main.java`:

```java
private static final String WHITE_SQUARE_COLOR = "#f0d9b5"; // Cream
private static final String BLACK_SQUARE_COLOR = "#b58863"; // Brown
```

Replace these hex values with any CSS color to theme the board to your liking. For example:

| Theme | Light Square | Dark Square |
|---|---|---|
| Classic (default) | `#f0d9b5` | `#b58863` |
| Blue Steel | `#dee3e6` | `#8ca2ad` |
| Green Felt | `#ffffdd` | `#86a666` |
| Dark Mode | `#b0b0b0` | `#404040` |

### Changing Board & Piece Size

```java
private static final int SQUARE_SIZE = 120; // Pixels per square
private static final int PIECE_SIZE  = 125; // Pixels per piece image
```

Increase or decrease `SQUARE_SIZE` to resize the entire board. Keep `PIECE_SIZE` slightly larger than `SQUARE_SIZE` for a natural overlap feel.

---

## 🔍 Game Features Deep Dive

### Drag & Drop System

The drag-and-drop system records mouse delta to smoothly track the piece under the cursor:

```java
private void handleMouseDragged(MouseEvent event) {
    double deltaX = event.getSceneX() - mouseX;
    double deltaY = event.getSceneY() - mouseY;
    ImageView image = selectedPiece.getSquare();
    image.setX(pieceX + deltaX);
    image.setY(pieceY + deltaY);
}
```

When the mouse is released, pixel coordinates are converted to grid coordinates to determine the target square.

### Pawn Rules (Most Complex Piece)

The pawn has three distinct move types handled in `isValidPawnMove()`:

```java
// 1. Normal forward move (1 square, no capture)
if (colDiff == 0 && rowDiff == 1 && targetPiece == null) return true;

// 2. Double advance from starting row
if (colDiff == 0 && row == 1 && rowDiff == 2 && targetPiece == null) return true;

// 3. Diagonal capture
if (isCaptureMove && rowDiff == 1 && Math.abs(colDiff) == 1) return true;
```

Black pawns move in the negative row direction (`rowDiff == -1`), and their starting double-advance checks `row == 6`.

### Path-Clear Guarantee

Before finalising any sliding piece move, `uperSe()` steps through every intermediate square between source and destination. If any square is non-null, the move is rejected and the piece snaps back:

```java
if (!uperSe(piece.getRow(), piece.getCol(), newRow, newCol)) {
    // Path is clear — execute the move
} else {
    // Path is blocked — snap back
    toOrginal(piece);
}
```

### Invalid Turn Alert

An animated alert appears when a player attempts to move the opponent's piece. The alert auto-dismisses after 1 second using a JavaFX `Timeline`:

```java
Alert alert = new Alert(AlertType.INFORMATION);
// ... set custom image content ...
alert.show();
Timeline timeline = new Timeline(
    new KeyFrame(Duration.seconds(1), e -> alert.close())
);
timeline.play();
```

---

## 🚀 Future Enhancements

These features are planned or would make great contributions:

- [ ] **♔ Check & Checkmate Detection** — Detect when a King is in check and end the game on checkmate *(logic already stubbed in comments)*
- [ ] **🤖 AI Opponent** — Single-player mode with a minimax or alpha-beta pruning AI
- [ ] **🏳 En Passant** — Special pawn capture rule
- [ ] **♙→♕ Pawn Promotion** — Promote a pawn that reaches the back rank
- [ ] **🏰 Castling** — King-side and queen-side castling
- [ ] **💾 Save & Load Game** — Serialize board state to a file
- [ ] **🌐 Online Multiplayer** — Play over a network using sockets
- [ ] **⏱️ Chess Clock** — Add a countdown timer per player
- [ ] **📜 Move History** — Display algebraic notation for all played moves
- [ ] **🎵 Sound Effects** — Audio feedback for moves and captures
- [ ] **📱 Relative Image Paths** — Remove hardcoded paths for better portability

---

## 🤝 Contributing

Contributions are warmly welcome! Here's how to get involved:

1. **Fork** this repository
2. **Create a feature branch**
   ```bash
   git checkout -b feature/add-castling
   ```
3. **Commit your changes** with a clear message
   ```bash
   git commit -m "feat: implement castling for both sides"
   ```
4. **Push** the branch to your fork
   ```bash
   git push origin feature/add-castling
   ```
5. **Open a Pull Request** against the `main` branch

### Contribution Ideas

- Fix the image path hardcoding to use `getClass().getResource()`
- Implement check/checkmate detection (logic is already commented in the source!)
- Add en passant, castling, or pawn promotion
- Write unit tests for `Piece.isValidMove()`
- Add a game reset button

Please keep code style consistent with the existing codebase and add comments for any new logic.

---

## 📚 References

| Resource | Link |
|---|---|
| FIDE Chess Rules (Official) | [fide.com](https://www.fide.com/fide/handbook.html?id=171&view=article) |
| JavaFX Documentation | [openjfx.io/javadoc](https://openjfx.io/javadoc/17/) |
| JavaFX Scene Graph Guide | [docs.oracle.com](https://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm) |
| JavaFX MouseEvent API | [JavaFX MouseEvent](https://openjfx.io/javadoc/17/javafx.graphics/javafx/scene/input/MouseEvent.html) |
| JavaFX Timeline & Animation | [JavaFX Animation](https://openjfx.io/javadoc/17/javafx.graphics/javafx/animation/Timeline.html) |
| Adoptium OpenJDK (Free JDK) | [adoptium.net](https://adoptium.net/) |
| JavaFX SDK Downloads | [gluonhq.com/products/javafx](https://gluonhq.com/products/javafx/) |

---

<div align="center">

Made with ❤️ and perseverance by **Dawood Technologies**

*"Ya Allah bacha la"* — a developer's prayer on day two of this project 😄

⭐ If you enjoyed this project, give it a star!

</div>
