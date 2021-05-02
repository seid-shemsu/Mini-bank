/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import javax.sql.DataSource;

/**
 *
 * @author Seid
 */
public class CustomerImplementation extends UnicastRemoteObject implements CustomerRemote {

    public CustomerImplementation() throws RemoteException {
        super();
    }

    @Override
    public void register(Customer customer) throws RemoteException {
        try {
            String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            String DB_URL = "jdbc:mysql://localhost:3306/customers";
            String USER = "root";
            String PASS = "";
            Connection conn = null;
            PreparedStatement stmt = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("connection to selected database");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("connected database successfully");
            System.out.println("creating statement");
            String sql = "insert into customer values (?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, customer.getPhone());
            stmt.setString(2, customer.getName());
            stmt.setInt(3, customer.getBalance());
            stmt.setString(4, customer.getPassword());
            stmt.executeUpdate();
            conn.close();
            System.out.print("done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean login(String phone, String password) throws RemoteException {
        boolean login = false;
        try {
            String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            String DB_URL = "jdbc:mysql://localhost:3306/customers";
            String USER = "root";
            String PASS = "";
            Connection conn = null;
            PreparedStatement stmt = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("connection to selected database");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("connected database successfully");
            System.out.println("creating statement");
            String sql = "select * from customer where phone = ? and password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, phone);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                login = true;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return login;
    }

    @Override
    public String deposite(String phone, int amount) throws RemoteException {
        int oldBalance = 0;
        try {
            String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            String DB_URL = "jdbc:mysql://localhost:3306/customers";
            String USER = "root";
            String PASS = "";
            Connection conn = null;
            PreparedStatement stmt = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("connection to selected database");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("connected database successfully");
            System.out.println("creating statement");
            String sql = "select balance from customer where phone = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                oldBalance = rs.getInt("balance");
            }
            oldBalance += amount;
            sql = "update customer set balance=? where phone=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, oldBalance);
            stmt.setString(2, phone);
            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String withdraw(String phone, int amount) throws RemoteException {
        int oldBalance = 0;
        try {
            String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            String DB_URL = "jdbc:mysql://localhost:3306/customers";
            String USER = "root";
            String PASS = "";
            Connection conn = null;
            PreparedStatement stmt = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("connection to selected database");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("connected database successfully");
            System.out.println("creating statement");
            String sql = "select balance from customer where phone = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                oldBalance = rs.getInt("balance");
            }
            if (oldBalance < amount) {
                return "error insufficient balance";
            } else {
                oldBalance -= amount;
                sql = "update customer set balance=? where phone=?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, oldBalance);
                stmt.setString(2, phone);
                stmt.executeUpdate();
                conn.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String transfer(String phone, String receiver, int amount) throws RemoteException {
        int senderOldBalance = 0;
        int receiverOldBalance = 0;
        try {
            String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            String DB_URL = "jdbc:mysql://localhost:3306/customers";
            String USER = "root";
            String PASS = "";
            Connection conn = null;
            PreparedStatement stmt = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("connection to selected database");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("connected database successfully");
            System.out.println("creating statement");
            String sqlSender = "select balance from customer where phone = ?";
            String sqlReceiver = "select balance from customer where phone = ?";
            stmt = conn.prepareStatement(sqlSender);
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                senderOldBalance = rs.getInt("balance");
            }
            if (senderOldBalance < amount) {
                return "error insufficient balance";
            } else {
                stmt = conn.prepareStatement(sqlReceiver);
                stmt.setString(1, receiver);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    receiverOldBalance = rs.getInt("balance");
                } else {
                    return "error user not found";
                }
                senderOldBalance -= amount;
                receiverOldBalance += amount;
                sqlSender = "update customer set balance=? where phone=?";
                sqlReceiver = "update customer set balance=? where phone=?";
                stmt = conn.prepareStatement(sqlSender);
                stmt.setInt(1, senderOldBalance);
                stmt.setString(2, phone);
                stmt.executeUpdate();
                stmt = conn.prepareStatement(sqlReceiver);
                stmt.setInt(1, receiverOldBalance);
                stmt.setString(2, receiver);
                stmt.executeUpdate();
                conn.close();
                System.out.print("done");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String balacne(String phone) throws RemoteException {
        //To change body of generated methods, choose Tools | Templates.
        try {
            int balance = 0;
            String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            String DB_URL = "jdbc:mysql://localhost:3306/customers";
            String USER = "root";
            String PASS = "";
            Connection conn = null;
            PreparedStatement stmt = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("connection to selected database");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("connected database successfully");
            System.out.println("creating statement");
            String sql = "select balance from customer where phone = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                balance = rs.getInt("balance");
            }
            return Integer.toString(balance);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public void update(String name, String phone, String password) throws RemoteException {
        try {
            String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            String DB_URL = "jdbc:mysql://localhost:3306/customers";
            String USER = "root";
            String PASS = "";
            Connection conn = null;
            PreparedStatement stmt = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("connection to selected database");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("connected database successfully");
            System.out.println("creating statement");

            String sql = "update customer set name=?, password=? where phone=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, password);
            stmt.setString(3, phone);
            long i = stmt.executeUpdate();
            System.out.print(i + "");
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int delete(String phone) throws RemoteException {
        int i = 0;
        try {
            String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            String DB_URL = "jdbc:mysql://localhost:3306/customers";
            String USER = "root";
            String PASS = "";
            Connection conn = null;
            PreparedStatement stmt = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("connection to selected database");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("connected database successfully");
            System.out.println("creating statement");
            String sql = "DELETE FROM customer WHERE phone=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, phone);
            i = stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

}
