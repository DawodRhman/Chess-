<div align="center">

# ♟️ Chess Game — by Dawood Technologies

**A fully playable, two-player chess game built with JavaFX**  
*Custom move-validation algorithms · Matrix-based board representation · Drag-and-drop gameplay*

![Java](https://img.shields.io/badge/Java-17%2B-007396?logo=java&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-17%2B-brightgreen?logo=java)
![License](https://img.shields.io/badge/license-MIT-blue)
![Status](https://img.shields.io/badge/status-active-success)

</div>

---

## 📖 Table of Contents

1. [Project Overview](#-project-overview)
2. [Features](#-features)
3. [Technologies Used](#️-technologies-used)
4. [Prerequisites](#-prerequisites)
5. [Installation & Setup](#-installation--setup)
6. [Usage](#-usage)
7. [Project Structure](#-project-structure)
8. [Customization — Piece Images](#-customization--piece-images)
9. [Contributing](#-contributing)
10. [License](#-license)

---

## 🎯 Project Overview

This is a **two-player chess game** built entirely from scratch using **JavaFX** for the graphical interface. Instead of relying on existing chess libraries, the game implements its own move-validation logic based on **8×8 matrix (2D array)** board representation — making the internal state transparent, efficient, and easy to extend.

Players interact with the board by **clicking and dragging** pieces. The game enforces turn order, validates every move according to standard chess rules for each piece type, and alerts the player visually if a move is illegal or out of turn.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🎲 **Full piece movement** | All six piece types implement correct chess movement rules |
| 🐴 **Knight L-shape logic** | Knights correctly jump over other pieces |
| 🛡️ **Path obstruction check** | Sliding pieces (rooks, bishops, queens) cannot pass through occupied squares |
| 🔄 **Turn enforcement** | Alternating White / Black turns with a pop-up reminder |
| ⚔️ **Capture logic** | Pieces can capture opponents but never friendly pieces |
| ♟️ **Pawn special moves** | Pawns support diagonal capture and the opening two-square advance |
| 🖱️ **Drag-and-drop UI** | Smooth mouse-driven interaction via JavaFX event handlers |
| 🖼️ **Custom piece images** | PNG images per piece — fully replaceable with your own artwork |
| 🎨 **Classic board colors** | Warm `#f0d9b5` / `#b58863` palette matching a traditional wooden board |

---

## 🛠️ Technologies Used

- **Java 17+** — core language
- **JavaFX 17+** — GUI framework (Scene, Stage, Group, ImageView, GridPane, Alert)
- **2D Array / Matrix** — `Piece[8][8]` as the authoritative board state
- **Custom algorithm library** — hand-written validators for every piece type (no external chess engine)
- **PNG assets** — 12 piece images (6 types × 2 colours) bundled with the project

### Why a Matrix?

```
Row 0  [ R ][ N ][ B ][ K ][ Q ][ B ][ N ][ R ]   ← White back rank
Row 1  [ P ][ P ][ P ][ P ][ P ][ P ][ P ][ P ]   ← White pawns
Row 2  [   ][   ][   ][   ][   ][   ][   ][   ]
  …
Row 6  [ p ][ p ][ p ][ p ][ p ][ p ][ p ][ p ]   ← Black pawns
Row 7  [ r ][ n ][ b ][ k ][ q ][ b ][ n ][ r ]   ← Black back rank
```

Every read and write is an **O(1)** array access. Move validation, capture detection, and path-checking all operate directly on `pieces[row][col]`.

---

## 📋 Prerequisites

| Requirement | Minimum version |
|---|---|
| Java Development Kit (JDK) | 17 |
| JavaFX SDK | 17 |
| IDE (Eclipse, IntelliJ IDEA, VS Code) | Any recent version |
| Operating System | Windows / macOS / Linux |

> **Tip:** Eclipse with the *e(fx)clipse* plug-in provides the smoothest setup for JavaFX projects. IntelliJ IDEA also works great with the VM arguments approach shown below.

---

## 🚀 Installation & Setup

### 1. Clone the repository

```bash
git clone https://github.com/DawodRhman/Chess-.git
cd Chess-
```

### 2. Download JavaFX SDK

Download the JavaFX SDK for your platform from the official site:  
👉 <https://gluonhq.com/products/javafx/>

Extract it somewhere convenient, e.g. `C:\javafx-sdk-17` (Windows) or `~/javafx-sdk-17` (macOS/Linux).

### 3. Import into your IDE

**Eclipse:**
1. *File → Import → Existing Projects into Workspace* → select the `chess/` folder.
2. Right-click the project → *Build Path → Configure Build Path → Libraries → Add External JARs* → select all JARs inside `<javafx-sdk>/lib/`.

**IntelliJ IDEA:**
1. *File → Open* → select the `chess/` folder.
2. *File → Project Structure → Libraries → + → Java* → select all JARs inside `<javafx-sdk>/lib/`.

### 4. Update image paths

> ⚠️ **Required before first run!** The image paths in `Main.java` are currently set to the original developer's machine. See the [Customization](#-customization--piece-images) section for step-by-step instructions.

### 5. Run the project

**Eclipse:** Right-click `Main.java` → *Run As → Java Application*

**Command line (after updating paths):**
```bash
javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml \
      -d out chess/src/application/Main.java chess/src/application/Piece.java

java  --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml \
      -cp out application.Main
```

---

## 🎮 Usage

1. Launch the application — a classic 8×8 chessboard appears.
2. **White moves first.** Click a white piece and drag it to the target square, then release.
3. If the move is valid (correct piece movement + no path obstruction + not landing on your own piece), the piece snaps to the new square.
4. If you try to move a piece out of turn, a friendly alert appears — just close it and let the other player go.
5. When a piece is captured it is removed from the board immediately.
6. Continue alternating turns until one side wins.

> **Keyboard shortcuts:** None required — the game is entirely mouse-driven.

---

## 📂 Project Structure

```
Chess-/
├── README.md
└── chess/
    ├── build.fxbuild          # Eclipse FX build descriptor
    ├── src/
    │   ├── module-info.java   # Java module declaration
    │   └── application/
    │       ├── Main.java      # Entry point: board setup, event handling, move execution
    │       ├── Piece.java     # Piece model: state, movement rules per type
    │       ├── application.css                     # (optional) stylesheet
    │       ├── chess-pawn-white.png
    │       ├── chess-pawn-black.png
    │       ├── chess-rook-white.png
    │       ├── chess-rook-black.png
    │       ├── chess-knight-white.png
    │       ├── chess-knight-black.png
    │       ├── chess-bishop-white.png
    │       ├── chess-bishop-black.png
    │       ├── chess-queen-white.png
    │       ├── chess-queen-black.png
    │       ├── chess-king-white.png
    │       └── chess-king-black.png
    └── bin/                   # Compiled .class files (auto-generated, do not edit)
```

### Key classes at a glance

| Class | Responsibility |
|---|---|
| `Main` | JavaFX `Application` subclass — initialises the board, places pieces, wires mouse events, validates and executes moves |
| `Piece` | Data model for a single chess piece — stores position, colour, type, image; contains all per-piece move-validation methods |

### Move validation flow

```
Mouse Released
     │
     ▼
movePiece(piece, newRow, newCol)
     │
     ├─ Wrong turn? → show alert, snap piece back
     │
     ├─ piece.isValidMove(newRow, newCol, targetPiece)
     │       ├─ Same cell? → false
     │       ├─ Friendly fire? → false
     │       └─ Delegate to type-specific validator
     │               (rook / knight / bishop / queen / king / pawn)
     │
     ├─ uperSe() — path obstruction check (skipped for knights; name is playful Urdu slang for "from above")
     │
     └─ Execute: update matrix, remove captured piece, redraw image
```

---

## 🖼️ Customization — Piece Images

All piece images are loaded via **absolute `file://` URLs** in `Main.java`. To make the project run on your machine (or to swap in your own artwork), update these paths.

### Quick find-and-replace

In `chess/src/application/Main.java`, find every occurrence of:

```java
"file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/
```

and replace it with the **absolute path** to the `chess/src/application/` folder on your machine.

**Windows example:**
```java
"file:///C:/Users/YourName/projects/Chess-/chess/src/application/chess-pawn-white.png"
```

**macOS / Linux example:**
```java
"file:///home/yourname/projects/Chess-/chess/src/application/chess-pawn-white.png"
```

### Using your own artwork

1. Prepare 12 PNG images (transparent background recommended, ~120×120 px).
2. Name them following the existing convention, or choose your own names.
3. Replace the path strings in `Main.java` with the paths to your new images.
4. Rebuild and run.

### Relative-path alternative (advanced)

To avoid hard-coded paths entirely, use the classpath resource loader:

```java
// In Piece.java constructor, replace:
this.square = new ImageView(new Image(imagePath));

// With:
this.square = new ImageView(
    new Image(Piece.class.getResourceAsStream(imagePath))
);

// Then pass only the filename, e.g.:
new Piece(0, 0, true, "rooks", "chess-rook-white.png")
```

This loads images from the same package as `Piece.java`, making the project fully portable.

---

## 🤝 Contributing

Contributions are very welcome! Here are some ideas for improvements:

- ♟ **En passant** and **castling** support
- 👑 **Pawn promotion** to queen (or player's choice)
- 🔍 **Check / Checkmate detection** (the scaffold is already in the code comments)
- 🤖 **Single-player AI** using minimax with alpha-beta pruning
- 🕐 **Chess clock** / time controls
- 💾 **Save & load** game state (PGN format)
- 🌐 **Online multiplayer** via sockets

### Steps to contribute

```bash
# 1. Fork the repository on GitHub
# 2. Create a feature branch
git checkout -b feature/your-feature-name

# 3. Make your changes and commit
git add .
git commit -m "feat: add your feature description"

# 4. Push and open a Pull Request
git push origin feature/your-feature-name
```

Please keep commits focused, write clear PR descriptions, and match the existing code style.

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

```
MIT License — Copyright (c) 2024 Dawood Technologies
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction...
```

---

<div align="center">

Made with ☕ and a lot of debugging comments by **Dawood Technologies**  
*"Ya Allah bacha la"* — the developer, somewhere around commit 7 😄

</div>
