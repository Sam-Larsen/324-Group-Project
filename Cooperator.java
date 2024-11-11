class Cooperator extends Player {

    public Cooperator(double energy, double alpha, double k, double enhancement) {
        super(energy, alpha, k, enhancement); 
    }

    @Override
    public void calc_payoff(int Cooperator, int Defector) {
        // Calculate payoff for Cooperator
        int basePayoff = (int) (this.enhancement * Cooperator / (Cooperator + Defector + 1));
        this.payoff += basePayoff - 1;
    }

    @Override
    public boolean Is_Cooperator() { 
        return true;
    }

    public Player switchType(double Payoff) {
        // Switch to Defector strategy
        return new Defector(this.payoff, this.alpha, this.k, this.enhancement);
    }
}
