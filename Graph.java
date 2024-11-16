import java.util.*;

public class Graph {
    
    private int size;
    private Map<Integer, List<Integer>> adjacencyList;
    private static final int SIZE_MIN_1D = 3;
    private static final int SIZE_MIN_2D = 9;

    public Graph(int size, String type) {
        this.size = size;
        adjacencyList = new HashMap<>();
        for (int i = 0; i < size; i++) {
            adjacencyList.put(i, new ArrayList<>());
        }
        generateLattice(type);
    }

    private void addEdge(int node1, int node2) {
        adjacencyList.get(node1).add(node2);
    }

    public void generateLattice(String type) {
        switch(type) {
            case "1D2n":
                generate1D2n();
                break;
            case "2D4n":
                generate2D4n();
                break;
            case "2D6n":
                generate2D6n();
                break;
            case "2D8n":
                generate2D8n();
                break;
            default:
                throw new IllegalArgumentException("Invalid lattice type. Use 1D2n, 2D4n, 2D6n, or 2D8n.");
        }
    }

    // 1D lattice with each node connected to its 2 nearest neighbors.
    private void generate1D2n() {
        if (size < SIZE_MIN_1D) {
            throw new IllegalArgumentException("Size must be at least " + SIZE_MIN_1D + " for 1D lattices.");
        } 

        for (int i = 0; i < size; i++) {
            addEdge(i, (i + 1) % size); // Connect to next node
            addEdge(i, (i - 1 + size) % size); // Connect to previous node (mod for periodic)
        }
    }

    // 2D lattice with each node connected to its 4 nearest neighbors.
    private void generate2D4n() {
        if (size < SIZE_MIN_2D) {
            throw new IllegalArgumentException("Size must be at least " + SIZE_MIN_2D + " for 2D lattices.");
        } 
        int side = (int) Math.sqrt(size);
        if (side * side != size) {
            throw new IllegalArgumentException("Size must be a perfect square for 2D lattices.");
        }

        for (int row = 0; row < side; row++) {
            for (int col = 0; col < side; col++) {
                int node = row * side + col;

                // Connect to right neighbor
                int right = row * side + (col + 1) % side;
                addEdge(node, right);

                // Connect to left neighbor
                int left = row * side + (col - 1 + side) % side;
                addEdge(node, left);

                // Connect to top neighbor
                int top = ((row - 1 + side) % side) * side + col;
                addEdge(node, top);

                // Connect to bottom neighbor
                int bottom = ((row + 1) % side) * side + col;
                addEdge(node, bottom);
            }
        }
    }

    // 2D lattice with each node connected to its 6 nearest neighbors (hexagonal structure approximation).
    private void generate2D6n() {
        // Connect to 4 nearest neighbors (same as 2D4n)
        generate2D4n();
        
        int side = (int) Math.sqrt(size);

        for (int row = 0; row < side; row++) {
            for (int col = 0; col < side; col++) {
                int node = row * side + col;

                // Add diagonal connections for hexagonal structure
                int topRight = ((row - 1 + side) % side) * side + (col + 1) % side;
                int bottomLeft = ((row + 1) % side) * side + (col - 1 + side) % side;

                addEdge(node, topRight);
                addEdge(node, bottomLeft);
            }
        }
    }

    // 2D lattice with each node connected to its 8 nearest neighbors (Moore neighborhood).
    private void generate2D8n() {
        if (size < SIZE_MIN_2D) {
            throw new IllegalArgumentException("Size must be at least " + SIZE_MIN_2D + " for 2D lattices.");
        }
        int side = (int) Math.sqrt(size);
        if (side * side != size) {
            throw new IllegalArgumentException("Size must be a perfect square for 2D lattices.");
        }

        for (int row = 0; row < side; row++) {
            for (int col = 0; col < side; col++) {
                int node = row * side + col;

                // Connect all neighbors including diagonals
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        if (dr == 0 && dc == 0) continue; // Skip self-connection
                        int neighborRow = (row + dr + side) % side;
                        int neighborCol = (col + dc + side) % side;
                        int neighbor = neighborRow * side + neighborCol;
                        addEdge(node, neighbor);
                    }
                }
            }
        }
    }

    public int getSize() {
        return size;
    }

    public List<Integer> getNeighbors(int node) {
        return adjacencyList.get(node);
    }


    // Rethink about the following get methods, now that the above structure only takes IDs into account. 
    // Players would need to be created, and Player's neighbors would need to be looked up by ID.

    //get all the players vertex ID
    public int[] get_Players(){
        return null;
    }

    //get a specific player
    public Player get_Player(int ID){
        return null;
    }

    // get all the neighbors of a specific palyer
    public Player[] get_Neighbors(int ID) {
        Player[] neighbors = new Player[4];

        return neighbors;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int node : adjacencyList.keySet()) {
            sb.append("Node ").append(node).append(": ").append(adjacencyList.get(node)).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int size = 16; // Example size for a 4x4 grid

        System.out.println("1D2n Lattice:");
        Graph graph1 = new Graph(size, "1D2n");
        System.out.println(graph1);

        System.out.println("2D4n Lattice:");
        Graph graph2 = new Graph(size, "2D4n");
        System.out.println(graph2);

        System.out.println("2D6n Lattice:");
        Graph graph3 = new Graph(size, "2D6n");
        System.out.println(graph3);

        System.out.println("2D8n Lattice:");
        Graph graph4 = new Graph(size, "2D8n");
        System.out.println(graph4);
    }
}

