class Defector extends Player {

    public Defector(int energy) {
        super(energy);
    }

    @Override
    public void calc_payoff() {
    }

    @Override
    public Player switchType() {
        return new Cooperator(this.payoff);  
    }
}