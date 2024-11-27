package src.main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.graphstream.graph.Edge;

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
    this.graph = new Graph(36, "2D4n");
    this.rounds = 3;
    this.alpha = 0.25;
    this.k = 0.5;
    this.enhancement = 1.5;
    this.numNeighbors = 4;
    this.players = new HashMap<>();
    List<Integer> defector = Arrays.asList(12);
    initializePlayer(36, defector);
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
          // // Select Random Player
          // Random random = new Random();
          // int randomIndex = random.nextInt(aliveNeighbors.size());
          // Integer randomPlayerIndex = aliveNeighbors.get(randomIndex);
          //System.out.println("select neighbor: "+randomPlayerIndex);

          // Select High Payoff Player
          Integer bestNeighborIndex = null;
          double highestPayoff = Double.NEGATIVE_INFINITY;
          for (Integer neighborIndex : aliveNeighbors) {
            double neighborPayoff = this.players.get(neighborIndex).getPayoff();
            if (neighborPayoff > highestPayoff) {
              highestPayoff = neighborPayoff;
              bestNeighborIndex = neighborIndex;
            }
          }
          // Switch if type are different
          if (
            this.players.get(playerIndex).isCooperator() !=
            this.players.get(bestNeighborIndex).isCooperator()
          ) {
            // Switch type using the random player's payoff
            this.players.put(
                playerIndex,
                this.players.get(playerIndex)
                  .switchType(this.players.get(bestNeighborIndex).getPayoff())
              );
            if (this.players.get(playerIndex).isCooperator()){
              graph.visualGraph.getNode(playerIndex).setAttribute("ui.class", "cooperator");
            }else{
              graph.visualGraph.getNode(playerIndex).setAttribute("ui.class", "defector");
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
     try {
       // Sleep for 5 seconds
       Thread.sleep(5000);
     } catch (InterruptedException e) {
       // Handle the exception if the thread is interrupted
       System.err.println("Sleep was interrupted: " + e.getMessage());
     }
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

  public Integer countAlivePlayer() {
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
    // System.out.println("AliveCooperator "+ aliveCooperator);
    // System.out.println("AliveDefector "+ aliveDefector);
    return aliveCooperator + aliveDefector;
  }

  public void runAutoGames() {
    // Check number of alive Cooperator and Defector
    Integer alivePlayer = countAlivePlayer();
    Integer countStability = 0;
    Integer round = 0;

    while (countStability < 5) {
      // System.out.println("Round: "+round);
      if (round == 0) {
        runOneGame(true);
        round++;
      } else {
        runOneGame(false);
        round++;
      }

      Integer currentAlivePlayer = countAlivePlayer();
      if (alivePlayer == currentAlivePlayer) {
        countStability++;
        System.out.println("countStability"+ countStability);
      } else {
        countStability = 0;
        System.out.println("countStability"+ countStability);
        alivePlayer = currentAlivePlayer;
      }
    }
    // System.out.println("Ended");
  }

  public Graph getGraph() {
    return graph;
  }
}
