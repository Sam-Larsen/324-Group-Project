package src.main;
import java.util.*;
public class Main {

  public static void main(String[] args) {
    System.setProperty("org.graphstream.ui", "swing");
    List<Integer> defector = Arrays.asList(4);
    Game game = new Game(9,"2D4n",1,4,0.2,0.5,1.5,defector);
    game.runOneGame();
    //System.out.print(game.players);
    // Set Parameter
    // Enhancement factor
    // Alpha
    // K
    // Graph size
    // Lattice Structure

    Graph graph = new Graph(9, "2D4n");
    // //Viewer viewer = graph.visualGraph.display();
    // // Let the layout work ...
    // viewer.disableAutoLayout();
  }
}
