

public class Graph {
    private Player[][] grid;
    private int rows;
    private int cols;

    public Graph(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Player[rows][cols];
        initialize_graph();
    }

    private void initialize_graph() {
    }

 
    public Player getPlayerAt(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return grid[row][col];
        }
        return null; 
    }

    public Player[] get_neighbors(int row, int col) {
        Player[] neighbors = new Player[4];

        return neighbors;
    }

}
