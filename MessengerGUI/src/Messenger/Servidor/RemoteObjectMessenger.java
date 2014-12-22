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
import Messenger.Utils.StringHEX;
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

    Object message = "Hello from RMI";
    //byte[] file;
    Key sharedKey;
    byte[] cryptMEssage;

    ConcurrentHashMap<String, CopyOnWriteArrayList> usersMessages;

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
        usersMessages.put(user, new CopyOnWriteArrayList());
        setSecretMessage(cryptMEssage, user);
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
    public byte[] getSecretMessage(String user) throws RemoteException {
        System.out.println("Getting message to" + user);
        return (byte []) usersMessages.get(user).remove(0);
    }

    @Override
    public void setSecretMessage(byte[] msg, String user) throws RemoteException {
        System.out.println("Setting message to" + user);
        usersMessages.get(user).add(msg);
    }
    
    
    
    
    
    
    
    

//    @Override
//    public boolean hasFiles(String user) throws RemoteException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public byte[] getFile(String user) throws RemoteException {
//        System.out.println("Getting message to" + user);
//        return (byte []) usersMessages.get(user).remove(0);
//    }
//
//    @Override
//    public void setFile(byte[] msg, String user) throws RemoteException {
//        this.file = msg;
//         usersMessages.get(user).add(msg);
//    }

}
