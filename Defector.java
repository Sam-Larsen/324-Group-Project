class Defector extends Player {

    public Defector(double energy, double alpha, double k, double enhancement) {
        super(energy, alpha, k, enhancement); 
    }

    @Override
    public void calc_payoff(int Cooperator, int Defector) { // need to do it accumulatively
        // Defectors benefit from cooperators but do not incur a cost
        this.payoff += (int) (this.enhancement * Cooperator / (Cooperator + Defector + 1));
    }

    @Override
    public boolean Is_Cooperator() { 
        return false;
    }

    @Override
    public Player switchType(double neighborPayoff) {
        if (neighborPayoff > this.payoff) {
            return new Defector(this.payoff, this.alpha, this.k, this.enhancement);
        }
        return this;
    }
}