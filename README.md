# Comparison of Scheduling Algorithms: Genetic Algorithm, Tabu Search, and Simulated Annealing

## Overview

This project compares the performance of three optimization algorithms for generating schedules. The algorithms being compared are:

1. **Genetic Algorithm (GA)**
2. **Tabu Search (TS)**
3. **Simulated Annealing (SA)**

Each algorithm is implemented in **Java** and is evaluated based on its ability to generate optimal or near-optimal schedules for a given set of tasks.

## Objective

The goal of this project is to evaluate and compare how well each algorithm performs in generating schedules. Key comparison metrics include:

- **Execution time**
- **Solution quality** (optimality)
- **Scalability** with the problem size
- **Robustness** to different initial configurations

## Algorithms

1. **Genetic Algorithm (GA):**
   - A nature-inspired algorithm based on the principles of natural selection.
   - It uses crossover, mutation, and selection to evolve solutions over multiple generations.

2. **Tabu Search (TS):**
   - A heuristic search algorithm that explores the solution space iteratively.
   - It uses a tabu list to avoid revisiting recently explored solutions and helps to escape local optima.

3. **Simulated Annealing (SA):**
   - A probabilistic search algorithm that mimics the process of annealing in metallurgy.
   - It allows the algorithm to move to worse solutions with a decreasing probability as the search progresses, helping to avoid local minima.

## Methodology

Each algorithm is applied to a scheduling problem, where tasks need to be assigned to time slots with the goal of minimizing a cost function, such as makespan, penalty, or task conflict.

### Steps:
1. **Initialization**: Create an initial population or solution.
2. **Iteration**: Each algorithm iterates to improve the current solution using its respective rules.
3. **Evaluation**: Compare results based on solution quality, computation time, and other metrics.
