package src.main;
import java.util.Arrays;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    System.setProperty("org.graphstream.ui", "swing");

    // Need to be changed
    String folder = "/Users/zhaochong/Desktop/CSC324/324-Group-Project/";
    String lattice = "2D4n";
    Integer numNeighbors = 4; // it is the number of neighbors (the number before n)
    Integer size = 1024;


    double[] kValues = {0.1,0.5,1.0,2.0};
    double[] enhancementValues = {5,10,15,20};
    double[] alphaValues = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
    Integer round = 10; 
    List<Integer> defector = Arrays.asList(12); // randomly choose one since they are connected

    // Tests for different alpha values
    for (double alpha : alphaValues) {
      String filename = String.format("%salpha_value_%.2f.csv", folder, alpha);

      // Create a new Game 
      Game game = new Game(size, lattice, round,numNeighbors, alpha , 0.1, 5, defector, filename);
      game.runAutoGames();

      System.out.printf("Completed game with alpha=%.2f\n", alpha);
    }

    // Tests for different k values
    for (double k : kValues) {
      String filename = String.format("%sk_value_%.2f.csv", folder, k);

      // Create a new Game 
      Game game = new Game(size, lattice, round,numNeighbors, 0.6, k, 5, defector, filename);
      game.runAutoGames();

      System.out.printf("Completed game with K=%.2f\n", k);
    }

    // // Tests for different alpha values
    for (double enhancement : enhancementValues) {
      String filename = String.format("%senhancement_value_%.2f.csv", folder, enhancement);

      // Create a new Game 
      Game game = new Game(size, lattice, round,numNeighbors, 0.6 , 0.1, enhancement, defector, filename);
      game.runAutoGames();

      System.out.printf("Completed game with enhancement=%.2f\n", enhancement);
    }

  }
}
