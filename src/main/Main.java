package src.main;

import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    System.setProperty("org.graphstream.ui", "swing");
    RandomUniqueIndicesWithSeed generator = new RandomUniqueIndicesWithSeed();
    List<Integer> defector =generator.generateRandomIndices(1024,123);

    // List<Double> kValues = new ArrayList<>();
    // for (double i = 0.1; i <= 3.0; i += 0.01) {
    //   kValues.add(Math.round(i * 100.0) / 100.0); // Round to 2 decimal places
    // }

    // List<Double> enhancementValues = new ArrayList<>();
    // for (double i = 1; i <= 20.1; i += 0.5) {
    //   enhancementValues.add(Math.round(i * 100.0) / 100.0); // Round to 2 decimal places
    // }

    List<Double> alphaValues = new ArrayList<>();
    for (double i = 0.1; i <= 1.0; i += 0.01) {
        alphaValues.add(Math.round(i * 100.0) / 100.0); // Round to 2 decimal places
    }
    Integer round = 10;


    // Need to be changed
    String folder1 = "/Users/zhaochong/Desktop/CSC324/324-Group-Project/2D8n/alpha/";
    String lattice = "2D8n";
    Integer numNeighbors = 8; // it is the number of neighbors (the number before n)
    Integer size = 1024;



    // Tests for different alpha values
    for (double alpha : alphaValues) {
      String filename = String.format("%salpha_value_%.2f.csv", folder1, alpha);

      // Create a new Game
      Game game = new Game(
        size,
        lattice,
        round,
        numNeighbors,
        alpha,
        0.1,
        10,
        defector,
        filename
      );
      game.runAutoGames();

      System.out.printf("Completed game with alpha=%.2f\n", alpha);
    }

    // // Tests for different k values
    // for (double k : kValues) {
    //   String filename = String.format("%sk_value_%.2f.csv", folder, k);

    //   // Create a new Game
    //   Game game = new Game(
    //     size,
    //     lattice,
    //     round,
    //     numNeighbors,
    //     0.3,
    //     k,
    //     10,
    //     defector,
    //     filename
    //   );
    //   game.runAutoGames();

    //   System.out.printf("Completed game with K=%.2f\n", k);
    // }


    // for (double enhancement : enhancementValues) {
    //   String filename = String.format(
    //     "%senhancement_value_%.2f.csv",
    //     folder,
    //     enhancement
    //   );

    //   // Create a new Game
    //   Game game = new Game(
    //     size,
    //     lattice,
    //     round,
    //     numNeighbors,
    //     0.3,
    //     0.1,
    //     enhancement,
    //     defector,
    //     filename
    //   );
    //   game.runAutoGames();

    //   System.out.printf("Completed game with enhancement=%.2f\n", enhancement);
    // }
}}
