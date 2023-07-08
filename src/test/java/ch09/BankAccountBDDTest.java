package ch09;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountBDDTest {
    @Test
    void shouldBeEmptyAfterCreation(){
        //GIVEN
        BankAccount bankAccount = new BankAccount();

        //WHEN
        int balance = bankAccount.getBalance();

        //THEN
        assertEquals(0, balance);
    }

    @Test
    void shouldAllowToCreditAccount(){
        //GIVEN
        BankAccount bankAccount = new BankAccount();

        //WHEN
        bankAccount.deposit(100);

        //THEN
        int balance = bankAccount.getBalance();
        assertEquals(100,balance);
    }

    @Test
    void shouldAllowToDebitAccount(){
        //GIVEN
        BankAccount bankAccount = new BankAccount();

        //WHEN
        bankAccount.deposit(100);
        bankAccount.withdraw(40);

        //THEN
        int balance = bankAccount.getBalance();
        assertEquals(60,balance);
    }
}
