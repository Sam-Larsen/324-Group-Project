class Defector extends Player {

    public Defector(int energy, double alpha, double k, double enhancement) {
        super(energy, alpha, k, enhancement); 
    }

    @Override
    public void calc_payoff(int num_alive_neighbor) {
    }

    @Override
    public Player switchType() {
        return new Cooperator(this.payoff, this.alpha, this.k, this.enhancement);  
    }
}