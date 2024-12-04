package src.main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class RandomUniqueIndicesWithSeed {
    public static List<Integer> generateRandomIndices(int n, long seed) {
        // Calculate the desired length (10% of n)
        int length = Math.max(1, n / 10); // Ensure at least one index is included for small n

        // Create a list of all indices from 0 to n-1
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            indices.add(i);
        }

        // Shuffle the list with a seeded Random instance
        Random random = new Random(seed);
        Collections.shuffle(indices, random);

        // Return the first 'length' elements as the result
        return indices.subList(0, length);
    }

    public static void main(String[] args) {
        int n = 100; // Example value for n
        long seed = 42L; // Example seed
        List<Integer> randomIndices = generateRandomIndices(n, seed);

        System.out.println("Random unique indices: " + randomIndices);
    }
}