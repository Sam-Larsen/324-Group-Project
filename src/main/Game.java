package src.main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Game {

  private Graph graph;
  private Integer rounds;
  private double alpha;
  private double k;
  private double enhancement;
  private Map<Integer, Player> players;
  private Integer numNeighbors;

  public Game(
    int size,
    String type,
    int rounds,
    int numNeighbors,
    double alpha,
    double k,
    double enhancement,
    List<Integer> defectors
  ) {
    this.graph = new Graph(size, type);
    this.rounds = rounds;
    this.alpha = alpha;
    this.k = k;
    this.enhancement = enhancement;
    this.numNeighbors = numNeighbors;
    this.players = new HashMap<>();
    initializePlayer(size, defectors);
  }
  public Game() {
    this.graph = new Graph(25, "2D4n");
    this.rounds = 10;
    this.alpha = 0.2;
    this.k = 0.5;
    this.enhancement = 1.5;
    this.numNeighbors = 4;
    this.players = new HashMap<>();
    List<Integer> defector = Arrays.asList(12);
    initializePlayer(25, defector);
  }

  public void initializePlayer(int size, List<Integer> defectors) {
    for (int index = 0; index < size; index++) {
      Player player;
      if (defectors.contains(index)) {
        player = new Defector(0, this.alpha, this.k, this.enhancement, index);
      } else {
        player = new Cooperator(0, this.alpha, this.k, this.enhancement, index);
      }

      // Add the player to the map
      this.players.put(index, player);
    }
  }

  public void runOneGame() {
    // Check for the player's switch strategy
    for (Map.Entry<Integer, Player> agent : players.entrySet()) {
      Integer playerIndex = agent.getKey();
      //Check whether they are alive
      if (!this.players.get(playerIndex).isAlive()) {
        continue;
      }

      // If alive find their neighbor (since we removed node all neighbors are alive)
      List<Integer> aliveNeighbors = this.graph.getNeighbors(playerIndex);
      if (!aliveNeighbors.isEmpty()) {
        Random random = new Random();
        int randomIndex = random.nextInt(aliveNeighbors.size());
        Integer randomPlayerIndex = aliveNeighbors.get(randomIndex);
        this.players.get(playerIndex)
          .switchType(this.players.get(randomPlayerIndex).getPayoff());
      }
    }

    // Reset the payoff to 0
    for (Map.Entry<Integer, Player> agent : players.entrySet()) {
      Integer playerIndex = agent.getKey();
      this.players.get(playerIndex).payoff = 0;
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
        this.graph.removeNode(playerIndex);
      }
    }
  }

  public void runMultipleGames() {
    for (int i = 0; i < this.rounds; i++) {
      runOneGame();
    }
  }
}
