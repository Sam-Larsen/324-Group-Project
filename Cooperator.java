class Cooperator extends Player {

    public Cooperator(int energy) {
        super(energy);
    }

    @Override
    public void calc_payoff() {
    }

    @Override
    public Player switchType() {
        System.out.println("Switching from Cooperator to Defector");
        return new Defector(this.payoff);  
    }
}
