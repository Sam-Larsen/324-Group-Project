package src.main;

public class Main {

  public static void main(String[] args) {
    System.setProperty("org.graphstream.ui", "swing");
    Game game = new Game();
    // True = draw graph with auto node alignment
    // False = draw graph nodes into grid
    game.getGraph().visualGraph.display(true);
    game.runAutoGames();
    //System.out.print(game.players);
    // Set Parameter
    // Enhancement factor
    // Alpha
    // K
    // Graph size
    // Lattice Structure

    //Graph graph = new Graph(9, "2D4n");
    // //Viewer viewer = graph.visualGraph.display();
    // // Let the layout work ...
    // viewer.disableAutoLayout();
  }
}
