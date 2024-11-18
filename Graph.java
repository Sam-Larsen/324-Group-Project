import java.util.*;

public class Graph {
    
    private static final int SIZE_DEFAULT = 10000;
    private static final String TYPE_DEFAULT = "2D4n";
    private static final String TYPE_1D2n = "1D2n";
    private static final String TYPE_2D4n = "2D4n";
    private static final String TYPE_2D6n = "2D6n";
    private static final String TYPE_2D8n = "2D8n";
    private static final int SIZE_MIN_1D = 3;
    private static final int SIZE_MIN_2D = 9;

    private int size = SIZE_DEFAULT;
    private String type = TYPE_DEFAULT;
    private Map<Integer, List<Integer>> adjacencyList = new HashMap<>();
    
    public Graph() {
        generateLattice();
    }

    public Graph(int size, String type) {
        this.size = size;
        this.type = type;
        generateLattice();
    }

    private void generateLattice() {
        adjacencyList.clear();
        for (int i = 0; i < size; i++) {
            adjacencyList.put(i, new ArrayList<>());
        }

        switch(type) {
            case TYPE_1D2n:
                checkSizeCompatibility1D();
                generate1D2n();
                break;
            case TYPE_2D4n:
                checkSizeCompatibility2D();
                generate2D4n();
                break;
            case TYPE_2D6n:
                checkSizeCompatibility2D();
                generate2D6n();
                break;
            case TYPE_2D8n:
                checkSizeCompatibility2D();
                generate2D8n();
                break;
            default:
                throw new IllegalArgumentException("Invalid lattice type: " + type + ". Use " + TYPE_1D2n + ", " + TYPE_2D4n + ", " + TYPE_2D6n + ", or " + TYPE_2D8n + ".");
        }
    }

    // 1D lattice with each node connected to its 2 nearest neighbors.
    private void generate1D2n() {
        for (int i = 0; i < size; i++) {
            addEdge(i, (i + 1) % size); // Connect to next node
            addEdge(i, (i - 1 + size) % size); // Connect to previous node (mod for periodic)
        }
    }

    // 2D lattice with each node connected to its 4 nearest neighbors.
    private void generate2D4n() {
        int side = (int) Math.sqrt(size);
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
                addEdge(node, topRight);

                int bottomLeft = ((row + 1) % side) * side + (col - 1 + side) % side;
                addEdge(node, bottomLeft);
            }
        }
    }

    // 2D lattice with each node connected to its 8 nearest neighbors (Moore neighborhood).
    private void generate2D8n() {
        int side = (int) Math.sqrt(size);
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

    private void checkSizeCompatibility1D() {
        if (size < SIZE_MIN_1D) {
            throw new IllegalArgumentException("Size must be at least " + SIZE_MIN_1D + " for a " + type + " lattice.");
        }
    }

    private void checkSizeCompatibility2D() {
        if (size < SIZE_MIN_2D) {
            throw new IllegalArgumentException("Size must be at least " + SIZE_MIN_2D + " for a " + type + " lattice.");
        } 
        int side = (int) Math.sqrt(size);
        if (side * side != size) {
            throw new IllegalArgumentException("Size must be a perfect square for a " + type + " lattice.");
        }
    }

    private void addEdge(int node1, int node2) {
        adjacencyList.get(node1).add(node2);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        generateLattice();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        generateLattice();
    }

    public List<Integer> getNeighbors(int node) {
        return adjacencyList.get(node);
    }

    public void removeNode(int node) {
        List<Integer> neighbors = adjacencyList.get(node);
        for (int neighbor : neighbors) {
            adjacencyList.get(neighbor).remove(Integer.valueOf(node));
        }
        neighbors.clear();
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
        // System.out.println("Default Lattice:");
        // Graph graphDefault = new Graph();
        // System.out.println(graphDefault);

        int size = 16; // Example size for a 4x4 grid

        System.out.println("1D2n Lattice:");
        Graph graph = new Graph(size, "1D2n");
        System.out.println(graph);

        System.out.println("2D4n Lattice:");
        graph.setType("2D4n");
        System.out.println(graph);

        System.out.println("2D6n Lattice:");
        graph.setType("2D6n");
        System.out.println(graph);

        System.out.println("2D8n Lattice:");
        graph.setType("2D8n");
        System.out.println(graph);


        System.out.println("1D2n Lattice:");
        // graph.setSize(12); // Be careful when setting size. New size may not be compatible with current lattice type.
        graph.setType("1D2n"); // Better to set type first then size
        graph.setSize(12);
        System.out.println(graph);
        
        System.out.println("2D4n Lattice:");
        // graph.setType("2D4n"); // Be careful when setting type. New type may not be compatible with current lattice size.
        graph.setSize(9); // Better to set size first then type
        graph.setType("2D4n"); 
        int removedNode = 2;
        System.out.println("Neighbors of node " + removedNode + " to be removed: " + graph.getNeighbors(2));
        graph.removeNode(removedNode);
        System.out.println(graph);

        System.out.println("2D6n Lattice:");
        graph.setType("2D6n");
        removedNode = 3;
        System.out.println("Neighbors of node " + removedNode + " to be removed: " + graph.getNeighbors(2));
        graph.removeNode(removedNode);
        System.out.println(graph);

        System.out.println("2D8n Lattice:");
        graph.setType("2D8n");
        removedNode = 4;
        System.out.println("Neighbors of node " + removedNode + " to be removed: " + graph.getNeighbors(2));
        graph.removeNode(removedNode);
        System.out.println(graph);
        removedNode = 5;
        System.out.println("Neighbors of node " + removedNode + " to be removed: " + graph.getNeighbors(2));
        graph.removeNode(removedNode);
        System.out.println(graph);
    }
}

