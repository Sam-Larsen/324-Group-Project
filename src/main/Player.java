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

  public boolean eliminateOrNot(int numNeighbor) {
    if (
      this.payoff <
      this.alpha *
      (this.enhancement-1) *
      (1 + numNeighbor)
    ) {
      this.alive = false;
      return true;
    } else {
      return false;
    }
  }

  public void Restart() {
    this.payoff = 0;
  }

  public abstract void calcPayoff(int Cooperator, int Defector);

  public abstract Player switchType(double Payoff);

  public abstract boolean isCooperator();
}
