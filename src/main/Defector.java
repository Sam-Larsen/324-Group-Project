class Defector extends Player {

    public Defector(double energy, double alpha, double k, double enhancement, int id) {
        super(energy, alpha, k, enhancement,id); 
    }

    @Override
    public void calcPayoff(int Cooperator, int Defector) { // need to do it accumulatively
        // Defectors benefit from cooperators but do not incur a cost
        this.payoff += (int) (this.enhancement * Cooperator / (Cooperator + Defector + 1));
    }

    @Override
    public boolean isCooperator() { 
        return false;
    }

    @Override
    public Player switchType(double neighborPayoff) {
        if (neighborPayoff > this.payoff) {
            return new Defector(this.payoff, this.alpha, this.k, this.enhancement, this.id);
        }
        return this;
    }
}