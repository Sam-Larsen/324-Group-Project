class Cooperator extends Player {

    public Cooperator(int energy, double alpha, double k, double enhancement) {
        super(energy, alpha, k, enhancement); 
    }

    @Override
    public void calc_payoff(int num_alive_neighbor) {

    }

    @Override
    public boolean Is_Cooperator() { 
        return true;
    }
}
