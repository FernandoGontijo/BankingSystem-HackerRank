package banking;

/**
 * Abstract bank account.
 */
public abstract class Account implements AccountInterface {
    private AccountHolder accountHolder;
    private Long accountNumber;
    private int pin;
    private double balance;

    protected Account(AccountHolder accountHolder, Long accountNumber, int pin, double startingDeposit) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = startingDeposit;
    }

    public AccountHolder getAccountHolder() {
     return accountHolder;
    }

    public boolean validatePin(int attemptedPin) {

        if (attemptedPin == pin) {
            return true;
        } else {
            return false;
        }
    }

    public double getBalance() {
        return balance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void creditAccount(double amount) {
        balance += amount;
    }

    public boolean debitAccount(double amount) {

        if(balance>=amount) {
            balance-=amount;
            return true;
        }
        return false;
    }
}
