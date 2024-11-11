import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Graph graph;

    public Game(int rows, int cols, int rounds) {
        this.graph = new Graph(rows, cols);  
    }

    public void runGame() {
        // Check for the player's switch strategy
        for (int player : this.graph.get_Players()) {
            List<Player> alive_neighbors = new ArrayList<>();
            for (Player neighbor : this.graph.get_Neighbors(player)) {
                if (neighbor.isAlive()) {
                    alive_neighbors.add(neighbor);
                }
            }
        
            if (!alive_neighbors.isEmpty()) {
                Random random = new Random();
                int randomIndex = random.nextInt(alive_neighbors.size());
                Player randomPlayer = alive_neighbors.get(randomIndex);
                this.graph.get_Player(player).switchType(randomPlayer.get_payoff());
            }
        }
        
        // Reset the payoff to 0
        for (int player : this.graph.get_Players()) {
            this.graph.get_Player(player).payoff= 0;
        }

        // Hosting game
        for (int player : this.graph.get_Players()) { 

            if (!this.graph.get_Player(player).isAlive()) {
                continue;
            }

            int num_Cooperator = 0;
            int num_Defector = 0;
            for (Player neighbor: this.graph.get_Neighbors(player)){
                if (neighbor.Is_Cooperator()){
                    num_Cooperator++;
                }else{
                    num_Defector++;
                }
            }

            for (Player neighbor: this.graph.get_Neighbors(player)){
                if(!neighbor.isAlive()){
                    continue;
                }
                neighbor.calc_payoff(num_Cooperator,num_Defector);
            }
        }


        // Check whether player is alive or not
        for(int player : this.graph.get_Players()){
            if (!this.graph.get_Player(player).isAlive()) {
                continue;
            }
            this.graph.get_Player(player).eliminate_or_not(this.graph.get_Neighbors(player).length);
        }
    }
}
