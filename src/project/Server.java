/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Seid
 */
public class Server {

    public static void main(String args[]) {
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            CustomerImplementation instant = new CustomerImplementation();
            reg.rebind("bank", instant);
            System.out.println("server running...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
