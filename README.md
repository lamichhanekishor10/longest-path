## Longest Path in a Directed Acyclic Graph (DAG)

This is a Java implementation to determine the longest directed path which begins at a particular node of a Directed Acyclic Graph (DAG). It works on all possible paths utilizing a depth-first method and identifying the path with the largest length.

---

## What Makes a Graph a DAG

Any graph that qualifies as a DAG must pass two tests.  
To start with, the graph must be oriented in such a way that every edge points in one direction towards another.  
Second, it must not have cycles. This is to mean that a path must not necessarily hit the same node twice.

---

## Overview of the Solution

The primary logic that is used to calculate the longest path is contained in the file GraphLongestPath.java in the src folder.

The graph is defined as having two classes: Node and Edge. A node maintains a list of outgoing edges that it uses to indicate which nodes it is connected to. It becomes easier to search every possible route.

The algorithm travels through the graph using depth first search (DFS). It begins with a selected node and will search through all the possible paths. It takes the number of the edges in each step and gives the length of the longest one.

The solution is applied to the search using memoization to make it efficient. A map receives the longest path length already determined on each node, hence the algorithm does not do the same job over and over. This enhances performance, in particular where there are overlaps in the graph.

The program maintains a list of the nodes being explored so as to manage cycles. When a node is visited again and is still under processing then there is some cycle. A DAG can never have any cycles hence an exception in such a situation.

---

## Input and Output

The program accepts as input a directed acyclic graph and an initial node. After which it calculates and prints the length of the longest path starting at that node. In the case of the node with no outgoing edges the output is 0.

---

## How to Run the Program

>Requirement: Java version 8 or higher

### Step 1 – Compile the Program
```bash
  javac GraphLongestPath.java
```
This command compiles the program and generates the class file.

### Step 2 – Run the Program
```bash
  java GraphLongestPath
```
The start of the program displays the maximum number of nodes of the sample graph.

Example output:
```
Longest path from node 1: 3
Longest path from node 2: 2
Longest path from node 3: 0
Longest path from node 4: 1
```
A node ID can also be passed as an argument to calculate the path only from that specific node:
```bash
  java GraphLongestPath 4
```
Output:
```
Longest path from node 4: 1
```

The method `createGraph()` defines a built-in sample graph. To test another graph, the method can be modified to add new nodes and edges.

---

## Running the Tests

The tests are placed inside `GraphLongestPathTest.java` and use **JUnit 5**.

---

## Questions

### 1. Does the solution work for larger graphs?

Yes. The large graphs are well performed by the program provided that the graph and memoization cache fit in memory. With the help of memoization, the algorithm does not repeat work on the already processed nodes. This enables it to deal with more complicated graphs in an effective manner.

However, the program is recursive and thus stack overflow errors may occur in very deep graphs. In these instances, a conversion of the recursion into an iterative method though a stack would be of assistance.

---

### 2. Can you think of any optimizations?

Yes. An example of the form of improvement would be instead of the recursive DFS, an iterative approach could be made so the program could be able to work with graphs of a higher level of depth. The other method is to use topological sorting to traverse nodes in a certain order and this would eliminate the use of recursion.

In graphs that have consecutive integer IDs arrays rather than maps may save on memory. Weighted edges can also be extended to the program storing and comparing total weights rather than just counting the edges.

---

### 3. What’s the computational complexity of your solution?    

As every node and edge is visited once during the DFS, the time complexity is O(V + E).  Due to the cache and recursion stack, the space complexity is O(V). 
In this case, V and E stand for the number of nodes and edges, respectively.
---

### 4. Are there any unusual cases that aren't handled?

The application only investigates nodes that can be reached from the specified starting node.  The computation excludes any further disconnected portions of the graph.  Additionally, all edges are assumed to have identical weight.  The cache needs to be erased by using the reset technique before working on another graph in the same run.

---

## Interview Discussion Questions   

### What are some things you don’t like about Java?

Comparing to other languages, Java code can often expand longer while addressing simple problems.  Even simple logic typically requires extra preparation, such as import statements and class definitions.  For instance, Java requires more steps than Python or JavaScript to print a few elements from a list.

Java apps may also take longer to launch.  When using serverless apps that need fast startup times or short-lived programs, this can be visible.

---

### If you could choose any language/framework/technology stack, what would you choose and why?

The type of project will decide which option is best.  A good option for a large-scale backend system that requires structure and stability is Java with Spring Boot.  For creating enterprise-level apps and APIs, it offers useful features.

considering its more basic syntax and number of tools, such pandas and scikit-learn, Python is frequently a superior option for jobs involving data analysis, automation, or artificial intelligence.

Frameworks such as React or Angular are useful for front-end development when combined with Java-based backends to create comprehensive online solutions.

---
