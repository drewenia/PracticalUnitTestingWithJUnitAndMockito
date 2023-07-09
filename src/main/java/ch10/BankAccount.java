package ch10;

public class BankAccount {
    private int balance;

    public void deposit(int amount){
        balance += amount;
    }
    public int getBalance() {
        return balance;
    }

    public void withdraw(int amount){
        balance -= amount;
    }
}
