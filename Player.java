
abstract class Player {
    protected int payoff;
    protected boolean alive;
    protected double alpha;
    protected double k;
    protected double enhancement;
    // other field of parameter *enahncement....

    public Player(int energy, double alpha, double k, double enhancement) {
        this.payoff = energy;
        this.alpha= alpha;
        this.k = k;
        this.enhancement=enhancement;
        this.alive = true;
    }

    public int get_payoff() {
        return this.payoff;
    }

    public void eliminate_or_not(int num_alive_neighbor) {
        if (this.payoff < this.alpha * (1-this.enhancement)*(1+num_alive_neighbor))
            this.alive = false;
    }

    public void Restart(){
        this.payoff=0;
    }

    public abstract void calc_payoff(int Cooperator, int Defector);

    public abstract Player switchType();

    public abstract boolean Is_Cooperator();

   
}