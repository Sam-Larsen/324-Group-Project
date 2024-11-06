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
        return payoff;
    }

    public void eliminate_or_not(int num_alive_neighbot) {
        if (this.payoff < this.alpha * (1-this.enhancement)*(1+num_alive_neighbot))
            this.alive = false;
    }

    public abstract void calc_payoff(int num_alive_neighbor);

    public abstract Player switchType();
}