package src.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.graphstream.graph.Edge;

public class Game {

  private Graph graph;
  private Integer rounds;
  private double alpha;
  private double k;
  private double enhancement;
  private Map<Integer, Player> players;
  private Integer numNeighbors;
  private Integer size;
  private String filename;

  public Game(
    int size,
    String type,
    int rounds,
    int numNeighbors,
    double alpha,
    double k,
    double enhancement,
    List<Integer> defectors,
    String filename
  ) {
    this.graph = new Graph(size, type);
    this.rounds = rounds;
    this.alpha = alpha;
    this.k = k;
    this.enhancement = enhancement;
    this.numNeighbors = numNeighbors;
    this.players = new HashMap<>();
    this.size = size;
    this.filename = filename;
    initializePlayer(size, defectors);
  }

  public Game() {
    this.size = 36;
    this.filename = null;
    this.graph = new Graph(size, "2D4n");
    this.rounds = 3;
    this.alpha = 0.25;
    this.k = 0.5;
    this.enhancement = 1.5;
    this.numNeighbors = 4;
    this.players = new HashMap<>();
    List<Integer> defector = Arrays.asList(12);
    initializePlayer(size, defector);
  }

  public void initializePlayer(int size, List<Integer> defectors) {
    for (int index = 0; index < size; index++) {
      Player player;
      if (defectors.contains(index)) {
        graph.visualGraph.getNode(index).setAttribute("ui.class", "defector");
        player = new Defector(0, this.alpha, this.k, this.enhancement, index);
      } else {
        graph.visualGraph.getNode(index).setAttribute("ui.class", "cooperator");
        player = new Cooperator(0, this.alpha, this.k, this.enhancement, index);
      }

      // Add the player to the map
      this.players.put(index, player);
    }
  }

  public void runOneGame(boolean firstGame) {
    // Check for the player's switch strategy
    if (!firstGame) {
      for (Map.Entry<Integer, Player> agent : players.entrySet()) {
        Integer playerIndex = agent.getKey();

        //Check whether they are alive
        if (!this.players.get(playerIndex).isAlive()) {
          continue;
        }

        // If alive find their neighbor (since we removed node all neighbors are alive)
        List<Integer> aliveNeighbors = this.graph.getNeighbors(playerIndex);
        if (!aliveNeighbors.isEmpty()) {
          // Select Random Player
          Random random = new Random();
          int randomIndex = random.nextInt(aliveNeighbors.size());
          Integer randomPlayerIndex = aliveNeighbors.get(randomIndex);
          //System.out.println("select neighbor: "+randomPlayerIndex);

          // Select High Payoff Player
          // Integer bestNeighborIndex = null;
          // double highestPayoff = Double.NEGATIVE_INFINITY;
          // for (Integer neighborIndex : aliveNeighbors) {
          //   double neighborPayoff = this.players.get(neighborIndex).getPayoff();
          //   if (neighborPayoff > highestPayoff) {
          //     highestPayoff = neighborPayoff;
          //     bestNeighborIndex = neighborIndex;
          //   }
          // }
          // Switch if type are different
          if (
            this.players.get(playerIndex).isCooperator() !=
            this.players.get(randomPlayerIndex).isCooperator()
          ) {
            // Switch type using the random player's payoff
            this.players.put(
                playerIndex,
                this.players.get(playerIndex)
                  .switchType(this.players.get(randomPlayerIndex).getPayoff())
              );
            if (this.players.get(playerIndex).isCooperator()) {
              graph.visualGraph
                .getNode(playerIndex)
                .setAttribute("ui.class", "cooperator");
            } else {
              graph.visualGraph
                .getNode(playerIndex)
                .setAttribute("ui.class", "defector");
            }
          }
        }
      }
      // Reset the payoff to 0
      for (Map.Entry<Integer, Player> agent : players.entrySet()) {
        Integer playerIndex = agent.getKey();
        this.players.get(playerIndex).payoff = 0;
      }
    }

    // Hosting game
    for (Map.Entry<Integer, Player> agent : players.entrySet()) {
      Integer playerIndex = agent.getKey();


      if (!this.players.get(playerIndex).isAlive()) {
        continue;
      }

      int num_Cooperator = 0;
      int num_Defector = 0;

      for (Integer neighborIndex : this.graph.getNeighbors(playerIndex)) {
        if (this.players.get(neighborIndex).isCooperator()) {
          num_Cooperator++;
        } else {
          num_Defector++;
        }
      }

      if (this.players.get(playerIndex).isCooperator()) {
        num_Cooperator++;
      } else {
        num_Defector++;
      }
      for (Integer neighborIndex : this.graph.getNeighbors(playerIndex)) { //Neighbors will be alive
        this.players.get(neighborIndex)
          .calcPayoff(num_Cooperator, num_Defector);
      }

      // Calculate its own payoff
      this.players.get(playerIndex).calcPayoff(num_Cooperator, num_Defector);
    }

    // Check whether player is alive or not
    for (Map.Entry<Integer, Player> agent : players.entrySet()) {
      Integer playerIndex = agent.getKey();
      if (!this.players.get(playerIndex).isAlive()) {
        continue;
      }

      if (this.players.get(playerIndex).eliminateOrNot(this.numNeighbors)) {
        // Disable edges of dying agent on visual graph
        graph.visualGraph.getNode(playerIndex).setAttribute("ui.class", "dead");
        for (Integer neighborIndex : this.graph.getNeighbors(playerIndex)) {
          String edgeId =
            Integer.toString(playerIndex) +
            ":" +
            Integer.toString(neighborIndex);
          String reverseEdgeId =
            Integer.toString(neighborIndex) +
            ":" +
            Integer.toString(playerIndex);
          Edge edge = graph.visualGraph.getEdge(edgeId) != null
            ? graph.visualGraph.getEdge(edgeId)
            : graph.visualGraph.getEdge(reverseEdgeId);
          edge.setAttribute("ui.class", "dead");
        }
        this.graph.removeNode(playerIndex);
      }
    }
    /* try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      System.err.println("Sleep was interrupted: " + e.getMessage());
    } */
  }

  public void runMultipleGames() {
    for (int i = 0; i < this.rounds; i++) {
      if (i == 0) {
        runOneGame(true);
      } else {
        runOneGame(false);
      }
    }
  }

  private void writeToFile(String filename, String content) {
    try (
      BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))
    ) {
      writer.write(content);
      writer.newLine();
    } catch (IOException e) {
      System.err.println("Error writing to file: " + e.getMessage());
    }
  }

  public void runAutoGames() {
    // Check number of alive Cooperator and Defector
    int alivePlayer = this.size;
    Integer countStability = 0;
    Integer round = 0;

    if (this.filename != null) {
      writeToFile(
        filename,
        "Round,AliveCooperator,AliveDefector,k,alpha,enhancement"
      );
    }

    while (countStability < 15) {
      if (round == 0) {
        runOneGame(true);
        round++;
      } else {
        runOneGame(false);
        round++;
      }

      // Count player
      Integer aliveCooperator = 0;
      Integer aliveDefector = 0;
      for (Map.Entry<Integer, Player> agent : players.entrySet()) {
        Integer playerIndex = agent.getKey();
        if (this.players.get(playerIndex).isAlive()) {
          if (this.players.get(playerIndex).isCooperator()) {
            aliveCooperator++;
          } else {
            aliveDefector++;
          }
        }
      }

      int currentAlivePlayer = aliveCooperator + aliveDefector;

      // Record the data
      if (this.filename != null) {
        String data = String.format(
          "%d,%d,%d,%.2f,%.2f,%.2f",
          round,
          aliveCooperator,
          aliveDefector,
          k,
          alpha,
          enhancement
        );
        writeToFile(this.filename, data);
      }

      if (alivePlayer == currentAlivePlayer) {
        countStability++;
        // System.out.println("countStability " + countStability);
      } else {
        countStability = 0;
        // System.out.println("countStability " + countStability);
        alivePlayer = currentAlivePlayer;
      }
    }
  }

  public Graph getGraph() {
    return graph;
  }
}
