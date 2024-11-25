package src.main;

class Defector extends Player {

  public Defector(
    double energy,
    double alpha,
    double k,
    double enhancement,
    int id
  ) {
    super(energy, alpha, k, enhancement, id);
  }

  @Override
  public void calcPayoff(int Cooperator, int Defector) { // need to do it accumulatively
    // Defectors benefit from cooperators but do not incur a cost
    this.payoff += this.enhancement * Cooperator / (Cooperator + Defector);
  }

  @Override
  public boolean isCooperator() {
    return false;
  }

  @Override
  public Player switchType(double neighborPayoff) {
    double random= Math.random();
    double prob = 1 / (1 + Math.exp(-(neighborPayoff-this.payoff) / this.k));

    if(random<prob){
      return new Cooperator(
        this.payoff,
        this.alpha,
        this.k,
        this.enhancement,
        this.id
      );

    }
    return this;
  }
}
