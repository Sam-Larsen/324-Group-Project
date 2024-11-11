import java.util.List;
import java.util.Random;

abstract class Player {
    protected double payoff;
    protected boolean alive;
    protected double alpha;
    protected double k;
    protected double enhancement;
    // other field of parameter *enahncement....

    public Player(double energy, double alpha, double k, double enhancement) {
        this.payoff = energy;
        this.alpha= alpha;
        this.k = k;
        this.enhancement=enhancement;
        this.alive = true;
    }

    public double get_payoff() {
        return this.payoff;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void eliminate_or_not(int num_alive_neighbor) {
        if (this.payoff < this.alpha * (1-this.enhancement)*(1+num_alive_neighbor))
            this.alive = false;
    }

    public void Restart(){
        this.payoff=0;
    }

    public abstract void calc_payoff(int Cooperator, int Defector);

    public abstract Player switchType(double Payoff);

    public abstract boolean Is_Cooperator();

    public void switch_or_not(List<Player> neighbors) {
        Random rand = new Random();

        for (Player neighbor : neighbors) {
            if (neighbor.isAlive() && neighbor.get_payoff() > this.payoff) {
                double probability = 1 / (1 + Math.exp((this.payoff - neighbor.get_payoff()) / this.k));

                if (rand.nextDouble() < probability) {
                    Player newType;

                    if (neighbor.Is_Cooperator()) {
                        newType = new Cooperator(this.payoff, this.alpha, this.k, this.enhancement);
                    } else {
                        newType = new Defector(this.payoff, this.alpha, this.k, this.enhancement);
                    }
                    this.payoff = newType.get_payoff();
                    this.alive = newType.isAlive();
                    break; // Only switch once per round
                }
            }
        }
    }
}