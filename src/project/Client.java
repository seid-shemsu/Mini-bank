/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.rmi.Naming;
import java.util.Scanner;

/**
 *
 * @author Seid
 */
public class Client {

    public static void main(String args[]) {
        try {
            CustomerRemote remote = (CustomerRemote) Naming.lookup("rmi://localhost:1099/bank");
//Registration code
            Scanner in = new Scanner(System.in);
//            System.out.print("phone : ");
//            String phone = in.next();
//            System.out.print("name : ");
//            String name = in.next();
//            System.out.print("password : ");
//            String password = in.next();
//            Customer customer = new Customer(phone, name, password, 0);
//            remote.register(customer);

//Deposite code
//            System.out.print("phone : ");
//            String phone = in.next();
//            System.out.print("deposite amount : ");
//            int amount = in.nextInt();
            //remote.deposite(phone, amount);
            
//Withdrawal code
//            System.out.print("phone : ");
//            String phone = in.next();
//            System.out.print("withdrawal amount : ");
//            int amount = in.nextInt();
            //remote.withdraw(phone, amount);
            
            
//Transfer code
//            System.out.print("phone : ");
//            String phone = in.next();
//            System.out.print("withdrawal amount : ");
//            int amount = in.nextInt();
//            System.out.print("reciever phone : ");
//            String rphone = in.next();
//            remote.transfer(phone, rphone, amount);
            
        } catch (Exception e) {

        }
    }
}
