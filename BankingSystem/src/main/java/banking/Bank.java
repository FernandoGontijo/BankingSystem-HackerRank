package banking;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * The Bank implementation.
 */
public class Bank implements BankInterface {
    private LinkedHashMap<Long, Account> accounts;

    public Bank() {
        accounts = new  LinkedHashMap<Long, Account>();
    }

    private Account getAccount(Long accountNumber) {
        return accounts.get(accountNumber);
    }

    public Long openCommercialAccount(Company company, int pin, double startingDeposit) {
        CommercialAccount commercialAccount = null;
        if(accounts.size()==0) {
            commercialAccount= new CommercialAccount(company, 1L, pin, startingDeposit);
        }else {
            Long []keys=accounts.keySet().toArray(new Long[accounts.size()]);
            commercialAccount= new CommercialAccount(company, (long) (keys[accounts.size()-1]+1), pin, startingDeposit);
        }
        accounts.put(commercialAccount.getAccountNumber(),commercialAccount);
        return commercialAccount.getAccountNumber();
    }

    public Long openConsumerAccount(Person person, int pin, double startingDeposit) {
        ConsumerAccount consumerAccount = null;
        if(accounts.size()==0) {
            consumerAccount= new ConsumerAccount(person, 1L, pin, startingDeposit);
        }else {
            Long []keys=accounts.keySet().toArray(new Long[accounts.size()]);
            consumerAccount= new ConsumerAccount(person, (long) (keys[accounts.size()-1]+1), pin, startingDeposit);
        }
        accounts.put(consumerAccount.getAccountNumber(), consumerAccount);
        return consumerAccount.getAccountNumber();
    }

    public double getBalance(Long accountNumber) {

        if(accounts.containsKey(accountNumber)) {
            return accounts.get(accountNumber).getBalance();
        }
        return -1;
    }

    public void credit(Long accountNumber, double amount) {
        if(accounts.containsKey(accountNumber)) {
            accounts.get(accountNumber).creditAccount(amount);
        }
    }

    public boolean debit(Long accountNumber, double amount) {
        if(accounts.containsKey(accountNumber)) {
            if(accounts.get(accountNumber).getBalance()>=amount) {
                accounts.get(accountNumber).debitAccount(amount);
                return true;
            }
            return false;
        }
        return false;

    }

    public boolean authenticateUser(Long accountNumber, int pin) {
        if(accounts.containsKey(accountNumber)) {
            return accounts.get(accountNumber).validatePin(pin);
        }
        return false;
    }
    
    public void addAuthorizedUser(Long accountNumber, Person authorizedPerson) {
        if(accounts.containsKey(accountNumber)) {
            Account account = accounts.get(accountNumber);

            if(account instanceof CommercialAccount) {

                CommercialAccount commercialAccount = (CommercialAccount) account;
                commercialAccount.addAuthorizedUser(authorizedPerson);
            } else {
                throw new IllegalArgumentException("Only Commercial Account can have authorized Users.");
            }
        } else {
            throw new IllegalArgumentException("Invaid account number: " + accountNumber);
        }

    }

    public boolean checkAuthorizedUser(Long accountNumber, Person authorizedPerson) {
        if(accounts.containsKey(accountNumber)) {
            Account account = accounts.get(accountNumber);

            if(account instanceof CommercialAccount) {
                CommercialAccount commercialAccount = (CommercialAccount) account;

                return commercialAccount.isAuthorizedUser(authorizedPerson);
            } else {

                throw new IllegalArgumentException("Only Commercial Account can have authorized Users.");
            }
        } else {

            throw new IllegalArgumentException("Invaid account number: " + accountNumber);
        }
    }

    public Map<String, Double> getAverageBalanceReport() {
        Map<String, Double> averageBalanceMap = new HashMap<>();

        double totalCommercialBalance = 0;
        int commercialCount = 0;
        double totalConsumerBalance = 0;
        int consumerCount = 0;

        for (Account account : accounts.values()) {
            if (account instanceof CommercialAccount) {
                totalCommercialBalance += account.getBalance();
                commercialCount++;
            } else if (account instanceof ConsumerAccount) {
                totalConsumerBalance += account.getBalance();
                consumerCount++;
            }
        }

        if (commercialCount > 0) {
            double averageCommercialBalance = totalCommercialBalance / commercialCount;
            averageBalanceMap.put("CommercialAccount", averageCommercialBalance);
        }

        if (consumerCount > 0) {
            double averageConsumerBalance = totalConsumerBalance / consumerCount;
            averageBalanceMap.put("ConsumerAccount", averageConsumerBalance);
        }

        return averageBalanceMap;
    }
}
