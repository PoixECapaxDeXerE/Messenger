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

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author manso
 */
public interface RemoteInterfaceMessenger extends Remote {

    public byte[] getSharedkey(PublicKey clientKey) throws RemoteException;

    public boolean connectUser(byte[] user,byte[] Bpass) throws RemoteException;

    public void disconnectUser(byte[] user) throws RemoteException;

    public boolean hasMessages(byte[] user) throws RemoteException;

    public Messages getSecretMessage(byte[] user) throws RemoteException;

    public void setSecretMessage(byte[] msg, byte[] user, byte[] UserDestination) throws RemoteException;

    public Messages getFile(byte[] user) throws RemoteException;

    public void setFile(byte[] msg, byte[] user, byte[] UserDestination, byte[] fileName) throws RemoteException;

    public byte[] getHash() throws RemoteException;

    public boolean hasUsers() throws RemoteException;

    public void setAvatar(byte[] user, byte[] arr) throws RemoteException;

    public byte[] getAvatar(byte[] user) throws RemoteException;
    
   
}
