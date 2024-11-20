package src.main;

abstract class Player {

  protected int id;
  protected double payoff;
  protected boolean alive;
  protected double alpha;
  protected double k;
  protected double enhancement;

  // other field of parameter *enahncement....

  public Player(
    double energy,
    double alpha,
    double k,
    double enhancement,
    int id
  ) {
    this.payoff = energy;
    this.alpha = alpha;
    this.k = k;
    this.enhancement = enhancement;
    this.alive = true;
    this.id = id;
  }

  public int getId() {
    return this.id;
  }

  public double getPayoff() {
    return this.payoff;
  }

  public boolean isAlive() {
    return this.alive;
  }

  public void eliminate_or_not(int num_alive_neighbor) {
    if (
      this.payoff <
      this.alpha *
      (1 - this.enhancement) *
      (1 + num_alive_neighbor)
    ) this.alive = false;
  }

  public void Restart() {
    this.payoff = 0;
  }

  public abstract void calc_payoff(int Cooperator, int Defector);

  public abstract Player switchType(double Payoff);

  public abstract boolean Is_Cooperator();
}
