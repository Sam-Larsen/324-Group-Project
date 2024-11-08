
public class Game {
    private Graph graph;

    public Game(int rows, int cols, int rounds) {
        this.graph = new Graph(rows, cols);  
    }

    public void runGame() {

        for (int player : this.graph.get_Players()) {
            this.graph.get_Player(player).switchType();
        }

        for (int player : this.graph.get_Players()) { // Hosting game
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
                
            }


        }

            
            

        //If the current game is not the first one
            // for each player
                // for each neighbor
                    //get neighbors payoff and store it in a sturcture (vector?)
                // player.switch()
        
        //for each player
            // for each neighbor
                // count num of cooperator
                // count num of defector

            // for each neighboer
                //if defector
                    // calculate payoff
                //else
                    // calculate payoff
            // calculate payoff for the player
        

        //for each player 
            // check death
    }
}
