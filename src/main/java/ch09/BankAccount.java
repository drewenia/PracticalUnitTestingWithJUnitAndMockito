package ch09;

public class BankAccount {
    private int balance;

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount){
        balance+=amount;
    }

    public void withdraw(int amount){
        balance -= amount;
    }
}
