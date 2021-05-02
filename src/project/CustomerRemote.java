/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Seid
 */
public interface CustomerRemote extends Remote{
    public void register(Customer customer) throws RemoteException;
    public boolean login(String phone, String password) throws RemoteException;
    public String deposite(String phone, int amount) throws RemoteException;
    public String withdraw(String phone, int amount) throws RemoteException;
    public String transfer(String phone, String receiver, int amount) throws RemoteException;
    public String balacne(String phone) throws RemoteException;
    public void update(String name, String phone, String password) throws RemoteException;
    public int delete(String phone) throws RemoteException;
}
