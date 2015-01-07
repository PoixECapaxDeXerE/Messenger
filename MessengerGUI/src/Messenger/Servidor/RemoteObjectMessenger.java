/**
 * **************************************************************************
 */
///****     Copyright (C) 2010                                             ****/
///****     António Manuel Rodrigues Manso                                  ****/
///****     e-mail: manso@ipt.pt                                            ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
///****     Instituto Politécnico de Tomar                                  ****/
///****     Escola Superior de Tecnologia de Tomar                          ****/
///****                                                                     ****/
///*****************************************************************************/
///****     This software was build with the purpose of learning.           ****/
///****     Its use is free and is not provided any guarantee               ****/
///****     or support.                                                     ****/
///****     If you met bugs, please, report them to the author              ****/
///****                                                                     ****/
///*****************************************************************************/
///*****************************************************************************/
package Messenger.Servidor;

import Messenger.Utils.Secrets;
import Messenger.Utils.Serializer;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.Key;
import java.security.PublicKey;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author manso
 */
public class RemoteObjectMessenger implements RemoteInterfaceMessenger {

    Object message = "Bem Vindo ao Chat!";
    Key sharedKey;
    byte[] cryptMEssage;
    String fileName;
    byte[] avatar;
   ConcurrentHashMap<String, CopyOnWriteArrayList<Messages>> usersMessages;

    public RemoteObjectMessenger() {
        try {
            sharedKey = Secrets.generateKey("AES");
            cryptMEssage = Secrets.encrypt(Serializer.toByteArray(message), sharedKey);
            usersMessages = new ConcurrentHashMap<>();
        } catch (IOException ex) {
            Logger.getLogger(RemoteObjectMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


   @Override
    public byte[] getSharedkey(PublicKey clientKey) throws RemoteException {
        try {
            byte[] data = Serializer.toByteArray(sharedKey);
            data = Secrets.encrypt(data, clientKey);
            return data;
        } catch (IOException ex) {
            Logger.getLogger(RemoteObjectMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }



    @Override
    public void connectUser(String user) throws RemoteException {
        System.out.println("connecting User" + user);
        usersMessages.put(user, new CopyOnWriteArrayList<Messages>());
        setSecretMessage(cryptMEssage, user,"txtStatus");
    }

    @Override
    public void disconnectUser(String user) throws RemoteException {
        System.out.println("Disconnecting User" + user);
        usersMessages.remove(user);
    }

    @Override
    public boolean hasMessages(String user) {
        return !usersMessages.get(user).isEmpty();
    }

    @Override
    public Messages getSecretMessage(String user) throws RemoteException {
        System.out.println("Getting message to" + user);     
        return  usersMessages.get(user).remove(0);
    }

    @Override
    public void setSecretMessage(byte[] msg, String user,String UserDestination) throws RemoteException {
        System.out.println("Setting message to" + user);
        usersMessages.get(user).add(new Messages(msg, UserDestination));
    }    
  
    @Override
    public ConcurrentHashMap getHash() throws RemoteException {
       return usersMessages;
    }

    @Override
    public boolean hasUsers() throws RemoteException {
        if(usersMessages.keySet().isEmpty())
            return false;
        return true;
    }  

    @Override
    public Messages getFile(String user) throws RemoteException {
         System.out.println("Getting message to" + user);     
        return  usersMessages.get(user).remove(0);
    }

    @Override
    public void setFile(byte[] msg, String user, String UserDestination, String fileName) throws RemoteException {
         System.out.println("Setting message to" + user);
        usersMessages.get(user).add(new Messages(msg, UserDestination,fileName));
    }

    @Override
    public void setAvatar(String user, byte[] arr) throws RemoteException {
         System.out.println("Setting message to" + user);    
    }

    @Override
    public byte[] getAvatar(String user) throws RemoteException {
     return Database.getAvatar(user);
    }

  


}
