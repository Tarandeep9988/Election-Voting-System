# Election Voting System

A Java implementation of three different election algorithms: **Plurality**, **Runoff** (instant-runoff voting), and **Tideman** (ranked pairs / Condorcet method).

## Overview

This project demonstrates object-oriented design by implementing three distinct voting methods in a clean, modular architecture.

### Algorithms

**Plurality Election**
- Single-choice voting system
- Winner: candidate with most votes
- Time: O(n), Space: O(m)

**Runoff Election**
- Ranked-choice voting with elimination rounds
- Winner: first candidate with >50% of votes
- Time: O(n·m²), Space: O(n·m)

**Tideman Election**
- Ranked pairs method with pairwise comparisons
- Uses DFS cycle detection to build preference graph
- Determines Condorcet winner if exists
- Time: O(m² + p·m²), Space: O(m² + p)

## Project Structure

```
src/election/
├── Main.java              # Entry point
├── algorithm/
│   ├── Plurality.java
│   ├── Runoff.java
│   └── Tideman.java
└── util/
    ├── InputUtil.java     # Ballot collection & validation
    └── OutputUtil.java    # Formatting
bin/                       # Compiled classes
```

## Build & Run

**Compile:**
```bash
mkdir -p bin && find src -name "*.java" -print0 | xargs -0 javac -d bin
```

**Run:**
```bash
java -cp bin election.Main
```

**VS Code:** Press F5

## Usage

1. Select election method (1-3)
2. Enter candidate names (space-separated)
3. Cast ballots:
   - Plurality: single ID per vote
   - Runoff/Tideman: space-separated IDs in preference order (e.g., `0 1 2`)
4. View results and algorithm details

## Design Highlights

- **OOP:** Inner classes (Candidate, Pair), proper encapsulation
- **Data Structures:** ArrayList, HashMap, 2D arrays for matrices
- **Features:** Input validation, duplicate detection, user feedback, cycle detection
- **Complexity:** Analyzed and optimized for each algorithm

## Improvements Over CS50

- Converted from procedural C to OOP Java
- Dynamic collections instead of fixed arrays
- Enhanced validation and error handling
- Better user experience with formatted output
- Graph algorithms (DFS) for cycle detection
