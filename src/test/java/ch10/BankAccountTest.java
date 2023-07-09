package ch10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountTest {
    private final BankAccount bankAccount = new BankAccount();

    @Test
    void shouldBeEmptyAfterCreation() {
        assertEquals(0,bankAccount.getBalance());
    }

    @Test
    void shouldAllowToCreditAccount() {
        bankAccount.deposit(100);
        assertEquals(100,bankAccount.getBalance());
        bankAccount.deposit(100);
        assertEquals(200,bankAccount.getBalance());
    }

    @Test
    void shouldAllowToDebitAccount(){
        bankAccount.deposit(100);
        bankAccount.withdraw(40);
        assertEquals(60,bankAccount.getBalance());
        bankAccount.withdraw(30);
        assertEquals(30,bankAccount.getBalance());
    }

    @Test
    void shouldNotAllowToWithdrawFromEmptyAccount(){
        // implementation omitted
    }

    @Test
    void shouldNotAllowToUseNegativeAmountForWithdraw(){
        // implementation omitted
    }

    @Test
    void shouldNotAllowToUseNegativeAmountForDeposit() {
        // implementation omitted
    }
}
