public record Money(int amount, String currency) {

    public Money{
        if (amount<0){
            throw new IllegalArgumentException("Illegal amount : [" + amount + "]");
        }
        if (currency == null || currency.isEmpty()){
            throw new IllegalArgumentException("Illegal currency : [" + currency + "]");
        }
    }

    @Override
    public boolean equals(Object anObject) {
        if (anObject instanceof Money money) {
            return money.currency().equals(currency()) && amount() == money.amount();
        }
        return false;
    }
}
