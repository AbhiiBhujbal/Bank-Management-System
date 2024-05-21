package Util;

import ConnectionHelper.CreateConnection;
import Model.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// DAO CLASS
public class BankOperationDAO implements BankImplementation {

    private static final Connection connection = CreateConnection.createConnection();
    private static final String createAccountQuery = "insert into bank values(?,?,?,?,?,?,?,?)";
    private static final String totalBalanceQuery = "SELECT total_balance FROM bank WHERE account_no=? ORDER BY total_balance DESC LIMIT 1";
    private static  String displayQuery="SELECT * FROM bank where account_no=?";
    @Override
    public int createAccount(Bank b) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(createAccountQuery);
            pstmt.setInt(1, 0);
            pstmt.setInt(2, b.getAccountNumber());
            pstmt.setString(3, b.getAccountHolderName());
            pstmt.setDouble(4, b.getDepositAmount());
            pstmt.setDouble(5, b.getWithdrawAmount());
            pstmt.setInt(6, b.getToAccount());
            pstmt.setInt(7, b.getFromAccount());
            pstmt.setDouble(8, b.getTotalBalance());
            int count = pstmt.executeUpdate();
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int depositAmount(Bank b) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(totalBalanceQuery);
            pstmt.setInt(1,b.getAccountNumber());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                //insert values
                PreparedStatement pstmt1= connection.prepareStatement(createAccountQuery);
                pstmt1.setInt(1,0);
                pstmt1.setInt(2,b.getAccountNumber());
                pstmt1.setString(3,"-");// name not accepted
                pstmt1.setDouble(4,b.getDepositAmount());
                pstmt1.setDouble(5,0);
                pstmt1.setInt(6,0);
                pstmt1.setInt(7,0);
                pstmt1.setDouble(8,rs.getDouble(1)+b.getDepositAmount());
            int count=  pstmt1.executeUpdate();
                return count;
            }
           return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int withdrawAmount(Bank b) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(totalBalanceQuery);
            pstmt.setInt(1,b.getAccountNumber());
            ResultSet rs = pstmt.executeQuery();
                if (rs.next() && rs.getDouble(1) >= b.getWithdrawAmount()) {
                        //insert values
                        PreparedStatement pstmt1 = connection.prepareStatement(createAccountQuery);
                        pstmt1.setInt(1, 0);
                        pstmt1.setInt(2, b.getAccountNumber());
                        pstmt1.setString(3, "-");// name not accepted
                        pstmt1.setDouble(4, 0);
                        pstmt1.setDouble(5, b.getWithdrawAmount());
                        pstmt1.setInt(6, 0);
                        pstmt1.setInt(7, 0);
                        pstmt1.setDouble(8, rs.getDouble(1)- b.getWithdrawAmount());
                        int count = pstmt1.executeUpdate();
                        return count;
                }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public  double checkbBalance(Bank b){
        try {
            PreparedStatement    pstmt = connection.prepareStatement(totalBalanceQuery);
            pstmt.setInt(1,b.getAccountNumber());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
            }
            return  0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public ArrayList<Bank> accountStatement(Bank b){
        ArrayList<Bank> bankList=new ArrayList<>();
        try {
            PreparedStatement pstmt= connection.prepareStatement(displayQuery);
            pstmt.setInt(1,b.getAccountNumber());
            ResultSet rs=pstmt.executeQuery();

            Bank b1=null;
            while (rs.next()){
                b1=new Bank();
                b1.setTransctionId(rs.getInt(1));
                b1.setAccountNumber(rs.getInt(2));
                b1.setAccountHolderName(rs.getString(3));
                b1.setDepositAmount(rs.getDouble(4));
                b1.setWithdrawAmount(rs.getDouble(5));
                b1.setToAccount(rs.getInt(6));
                b1.setFromAccount(rs.getInt(7));
                b1.setTotalBalance(rs.getDouble(8));
                bankList.add(b1);
            }
            return bankList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public  int transferMoney(Bank b){
        try {
            PreparedStatement pstmt= connection.prepareStatement(createAccountQuery);
            pstmt.setInt(1,0);
            pstmt.setInt(2,0);
            pstmt.setString(3,"-");
            pstmt.setDouble(4,0);
            pstmt.setDouble(5,0);
            pstmt.setInt(6,b.getToAccount());
            pstmt.setInt(7,b.getFromAccount());
            pstmt.setDouble(8,b.getTotalBalance());
            int count = pstmt.executeUpdate();

            depositAmount( b);
            withdrawAmount(b);
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}

