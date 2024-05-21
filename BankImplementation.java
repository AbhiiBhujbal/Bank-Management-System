package Util;

import Model.Bank;

import java.util.ArrayList;

public interface BankImplementation {
    public int createAccount(Bank b);
    public int depositAmount(Bank b);

    public  int withdrawAmount(Bank b);
    public  double checkbBalance(Bank b);

    public ArrayList<Bank> accountStatement(Bank b);
    public  int transferMoney(Bank b);
}
