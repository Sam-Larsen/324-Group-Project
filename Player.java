abstract class Player {
    protected int payoff;
    protected boolean alive;
    // other field of parameter *enahncement....

    public Player(int energy) {
        this.payoff = energy;
        this.alive = true;
    }

    public int get_payoff() {
        return payoff;
    }

    public void eliminate_or_not() {
    }

    public abstract void calc_payoff();

    public abstract Player switchType();
}