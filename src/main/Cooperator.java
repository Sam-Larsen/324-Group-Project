class Cooperator extends Player {

    public Cooperator(double energy, double alpha, double k, double enhancement, int id) {
        super(energy, alpha, k, enhancement, id); 
    }

    @Override
    public void calcPayoff(int Cooperator, int Defector) {
        // Calculate payoff for Cooperator
        int basePayoff = (int) (this.enhancement * Cooperator / (Cooperator + Defector + 1));
        this.payoff += basePayoff - 1;
    }

    @Override
    public boolean isCooperator() { 
        return true;
    }

    public Player switchType(double neighborPayoff) {
        if (neighborPayoff > this.payoff) {
            return new Defector(this.payoff, this.alpha, this.k, this.enhancement,id);
        }
        return this;
    }
}
