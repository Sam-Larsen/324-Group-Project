class Cooperator extends Player {

    public Cooperator(int energy, double alpha, double k, double enhancement) {
        super(energy, alpha, k, enhancement); 
    }

    @Override
    public void calc_payoff(int Cooperator, int Defector) {
        // Calculate payoff for Cooperator
        int basePayoff = (int) (this.enhancement * Cooperator / (Cooperator + Defector + 1));
        this.payoff += basePayoff - 1;  // Cost of cooperation (set to 1 as per convention)
    }

    @Override
    public boolean Is_Cooperator() { 
        return true;
    }

    public Player switchType() {
        // Switch to Defector strategy
        return new Defector(this.payoff, this.alpha, this.k, this.enhancement);
    }
}
