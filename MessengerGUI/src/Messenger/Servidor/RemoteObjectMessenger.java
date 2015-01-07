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
    public boolean connectUser(byte[] Buser, byte[] Bpass) throws RemoteException {
        String user = null;
        String pass = null;
        try {
            user = (String) Serializer.toObject(Secrets.decrypt(Buser, sharedKey));
            pass = (String) Serializer.toObject(Secrets.decrypt(Bpass, sharedKey));
        } catch (Exception ex) {
            Logger.getLogger(RemoteObjectMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (Database.userExists(user)) {
            if (Database.login(user, pass)) {
                System.out.println("connecting User" + user);
                usersMessages.put(user, new CopyOnWriteArrayList<Messages>());
                //encriptar "txtStatus"
                byte[] m = null;
                try {
                    m = Secrets.encrypt(Serializer.toByteArray("txtStatus"), sharedKey);
                } catch (IOException ex) {
                    Logger.getLogger(RemoteObjectMessenger.class.getName()).log(Level.SEVERE, null, ex);
                }
                setSecretMessage(cryptMEssage, Buser, m);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void disconnectUser(byte[] user1) throws RemoteException {
        String user = null;
        try {
            user = (String) Serializer.toObject(Secrets.decrypt(user1, sharedKey));
        } catch (Exception ex) {
            Logger.getLogger(RemoteObjectMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Disconnecting User" + user);
        usersMessages.remove(user);
    }

    @Override
    public boolean hasMessages(byte[] user1) {
        String user = null;
        try {
            user = (String) Serializer.toObject(Secrets.decrypt(user1, sharedKey));
        } catch (Exception ex) {
            Logger.getLogger(RemoteObjectMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }
        return !usersMessages.get(user).isEmpty();
    }

    @Override
    public Messages getSecretMessage(byte[] user1) throws RemoteException {
        String user = null;
        try {
            user = (String) Serializer.toObject(Secrets.decrypt(user1, sharedKey));
        } catch (Exception ex) {
            Logger.getLogger(RemoteObjectMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Getting message to" + user);
        return usersMessages.get(user).remove(0);
    }

    @Override
    public void setSecretMessage(byte[] msg, byte[] user1, byte[] UserDestination) throws RemoteException {
        try {
            String user = null;
            user = (String) Serializer.toObject(Secrets.decrypt(user1, sharedKey));
            System.out.println("Setting message to" + user);
            usersMessages.get(user).add(new Messages(msg, UserDestination));
        } catch (Exception ex) {
            Logger.getLogger(RemoteObjectMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public byte[] getHash() throws RemoteException {
        byte[] hash = null;
        try {
            hash = Secrets.encrypt(Serializer.toByteArray(usersMessages), sharedKey);
        } catch (IOException ex) {
            Logger.getLogger(RemoteObjectMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hash;
    }

    @Override
    public boolean hasUsers() throws RemoteException {
        if (usersMessages.keySet().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public Messages getFile(byte[] user1) throws RemoteException {
        String user = null;
        try {
            user = (String) Serializer.toObject(Secrets.decrypt(user1, sharedKey));
            System.out.println("Getting message to" + user);

        } catch (Exception ex) {
            Logger.getLogger(RemoteObjectMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usersMessages.get(user).remove(0);
    }

    @Override
    public void setFile(byte[] msg, byte[] user1, byte[] UserDestination, byte[] fileName) throws RemoteException {
        try {
            String user = (String) Serializer.toObject(Secrets.decrypt(user1, sharedKey));
            System.out.println("Setting message to" + user);
            usersMessages.get(user).add(new Messages(msg, UserDestination, fileName));
        } catch (Exception ex) {
            Logger.getLogger(RemoteObjectMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setAvatar(byte[] user1, byte[] arr) throws RemoteException {
        try {
            String user = (String) Serializer.toObject(Secrets.decrypt(user1, sharedKey));
            System.out.println("Setting message to" + user);
        } catch (Exception ex) {
            Logger.getLogger(RemoteObjectMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public byte[] getAvatar(byte[] user1) throws RemoteException {
        String user = null;
        try {
            user = (String) Serializer.toObject(Secrets.decrypt(user1, sharedKey));

        } catch (Exception ex) {
            Logger.getLogger(RemoteObjectMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Database.getAvatar(user);
    }

    @Override
    public boolean userExists(byte[] user1) throws RemoteException {
        String user = null;
        try {
            user = (String) Serializer.toObject(Secrets.decrypt(user1, sharedKey));

        } catch (Exception ex) {
            Logger.getLogger(RemoteObjectMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (Database.userExists(user)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean insertUser(byte[] user1, byte[] pass1, byte[] Q1, byte[] A1, byte[] avatar) throws RemoteException {
        String user = null;
        String pass = null;
        String Q = null;
        String A = null;
        try {
            user = (String) Serializer.toObject(Secrets.decrypt(user1, sharedKey));
            pass = (String) Serializer.toObject(Secrets.decrypt(pass1, sharedKey));
            Q = (String) Serializer.toObject(Secrets.decrypt(Q1, sharedKey));
            A = (String) Serializer.toObject(Secrets.decrypt(A1, sharedKey));

        } catch (Exception ex) {
            Logger.getLogger(RemoteObjectMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (Database.insert(user, pass, Q, A, avatar)) {
            return true;
        }else 
            return false;
    }

}
