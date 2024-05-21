package Util;

import Model.Bank;

import java.util.ArrayList;
import java.util.Scanner;

public class MainOperation {
    private  static Scanner scanner=new Scanner(System.in);
    private  static BankImplementation bankInterface=new BankOperationDAO() ;
    public static void main(String[] args) {

       operation();
    }
    private static  void operation(){
        boolean status=true;
        while (status) {
            System.out.println("SELECT MODE OF OPERATION");
            System.out.println("1:CREATE ACCOUNT ");
            System.out.println("2:PERFORM OPERATION");
            System.out.println("ENTER YOUR CHOICE");
            int choice = scanner.nextInt();
            if (choice == 1) {
                createAccount();

            } else if (choice == 2) {
                performTransction();

            } else {
                System.out.println("INVALID CHOICE");
            }
        }
    }
    private  static  void createAccount(){
        System.out.println("ENTER ACCOUNT NUMBER");
        int accountNumber= scanner.nextInt();
        System.out.println("ENTER ACCOUNT HOLDER NAME");
        String accountHoldername= scanner.next();
        System.out.println("ENTER INITIAL AMOUNT");
        double initialBalance= scanner.nextDouble();
        if(initialBalance>=5000){
            // set values in DTO class
            Bank b1=new Bank();
            b1.setAccountHolderName(accountHoldername);
            b1.setAccountNumber(accountNumber);
            b1.setDepositAmount(initialBalance);
            b1.setTotalBalance(initialBalance);

            int result=bankInterface.createAccount(b1);
            if (result > 0) {
                System.out.println("DATA INSERTED");
            }else{
                System.out.println("SOMETHING WENT WRONG");
            }
            System.out.println("ACCOUNT CREATED SUCCESSFULLY");
        }else{
            System.out.println("INVALID AMOUNT");
        }
    }
    private static void performTransction(){
        boolean status=true;
        while(status) {
            System.out.println("SELECT MODE OF TRANSCTION");
            System.out.println("1:DEPOSIT");
            System.out.println("2:WITHDRAW");
            System.out.println("3:CHECK BALANCE");
            System.out.println("4:ACCOUNT STATEMENT");
            System.out.println("5:TRANSFER");
            System.out.println("6:EXIT");
            int choice= scanner.nextInt();
            switch (choice){
                case 1:
                    depositAmount();
                    break;
                case 2:
                    withdrawAmount();
                    break;
                case 3:
                    checkBalance();
                    break;
                case 4:
                    accountStatement();
                    break;

                case 5:
                        transferMoney();
                    break;
                case 6:
                    status=false;
                    break;
            }
        }
    }

    private static void transferMoney() {
        System.out.println("ENTER ACCOUNT NUMBER TO TRANSFER MONEY TO ACCOUNT");
        int toAccount= scanner.nextInt();
        System.out.println("ENTER ACCOUNT NUMBER TO TRANSFER MONEY FROM ACCOUNT");
        int fromAccount= scanner.nextInt();
        System.out.println("ENTER AMOUNT ");
        int amt= scanner.nextInt();;

        Bank b=new Bank();
        b.setFromAccount(fromAccount);
        b.setToAccount(toAccount);
        BankImplementation bi=new BankOperationDAO();
        int result= bi.transferMoney(b);
       if (result>0){
           System.out.println(result+"DATA  INSERTED");
       }else{
           System.out.println("SOMETHING WENT WRONG");
       }
    }
    private static void accountStatement() {
        System.out.println("ENTER ACCOUNT NUMBER");
        int accNo= scanner.nextInt();
        Bank b=new Bank();
        b.setAccountNumber(accNo);
        BankImplementation bi=  new BankOperationDAO();
        ArrayList<Bank>  banksData=bi.accountStatement(b);
        System.out.println("TRANSCTION_ID\tACCOUNT_NUMBER\tACOOUNT_HOLDER_NAME\tDEPOSIT_AMOUNT\tWITHDRAW_AMOUNT\tTO_ACCOUNT\tFROM_ACCOUNT\tTOTAL_BALANCE");
        for(Bank s:banksData){
            System.out.println(s.getTransctionId()+"   \t        "+s.getAccountNumber()+"   \t        "+s.getAccountHolderName()+"   \t        "+s.getDepositAmount()+"    \t       "+s.getWithdrawAmount()+"    \t     "+s.getToAccount()+"   \t       "+s.getFromAccount()+"     \t      "+s.getTotalBalance());
        }
    }
    private static void checkBalance() {
        System.out.println("ENTER ACCOUNT NUMBER");
        int accNo= scanner.nextInt();
         Bank b1=new Bank();
         b1.setAccountNumber(accNo);
        BankImplementation bi=new BankOperationDAO();
        double result= bi.checkbBalance(b1);
        if(result>0){
            System.out.println("ACCOUNT BALANCE"+result);
        }else{
            System.out.println("SOMETHING WENT WRONG");
        }
    }

    private static void depositAmount(){
        System.out.println("ENTER ACCOUNT NUMBER");
        int accNo= scanner.nextInt();
        System.out.println("ENTER DEPOSIT AMOUNT");
        double depositAmt= scanner.nextDouble();
        if(depositAmt>0){
            Bank b1=new Bank();
            b1.setAccountNumber(accNo);
            b1.setDepositAmount(depositAmt);
            BankImplementation bi=new BankOperationDAO();
            int result= bi.depositAmount(b1);
            if(result>0){
                System.out.println("AMOUNT DEPOSITED SUCCESSFULLY");
            }else{
                System.out.println("INVALID AMOUNT");
            }
        }else{
            System.out.println("INVALID AMOUNt");
        }

    }
    private static void withdrawAmount(){
        System.out.println("ENTER ACCOUNT NUMBER");
        int accNo= scanner.nextInt();
        System.out.println("ENTER WITHDRAW AMOUNT");
        double withdrawAmt= scanner.nextDouble();
        if(withdrawAmt>0) {
            Bank b1 = new Bank();
            b1.setAccountNumber(accNo);
            b1.setWithdrawAmount(withdrawAmt);

         BankImplementation bi=new BankOperationDAO();
                 int result=bi.withdrawAmount(b1);
            if (result > 0) {
                System.out.println("AMOUNT DEBITED SUCCESSFULLY");
            } else {
                System.out.println("INVALID AMOUNT");
            }
        }
    }
}
