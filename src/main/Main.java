package src.main;

import org.graphstream.ui.view.Viewer;

public class Main {

  public static void main(String[] args) {
    System.setProperty("org.graphstream.ui", "swing");
    // Set Parameter
    // Enhancement factor
    // Alpha
    // K
    // Graph size
    // Lattice Structure

    Graph graph = new Graph(100, "2D4n");
    Viewer viewer = graph.visualGraph.display();
    // Let the layout work ...
    viewer.disableAutoLayout();
  }
}
