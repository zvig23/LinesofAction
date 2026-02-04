# Lines of Action – Minimax AI Project

## Overview

This repository contains **my personal project** for solving the game **Lines of Action (LOA)** using Artificial Intelligence techniques.

The focus of the project is the **game-solving logic**, not the graphical interface.
The UI used in this project was taken from an external source I found online and then adapted to work with my AI engine. The original UI is used strictly as a visualization and interaction layer.

## What I Built

The core contribution of this project is an AI player based on:

* **Minimax search tree**
* **Alpha-Beta Pruning** for performance optimization
* Custom **heuristic evaluation functions** for LOA board states

The AI evaluates game states, searches possible move sequences, and selects optimal moves according to the minimax principle while efficiently pruning irrelevant branches.

## Key Features

* Full implementation of **Minimax with Alpha-Beta Pruning**
* Game-state evaluation tailored specifically for **Lines of Action**
* Modular design separating:

  * Game logic
  * Search algorithm
  * Heuristic evaluation
  * UI layer

## What This Project Is NOT

* This is **not** a UI-focused project
* The UI was **not originally written by me** and is only used as a base framework
* This repository does **not** represent a generic or instructor-provided template

## Purpose

This project was built to:

* Practice and demonstrate adversarial search algorithms
* Explore heuristic design for board games
* Apply theoretical AI concepts (Minimax, Alpha-Beta Pruning) in a full working game

## Technologies

* Java
* Minimax Search
* Alpha-Beta Pruning

## Disclaimer

All AI logic, heuristics, and search implementations are my own work. Credit for the UI belongs to its original source.

---

If you are interested in the AI logic, heuristics, or search optimizations, focus on the game engine and minimax-related classes rather than the UI components.
