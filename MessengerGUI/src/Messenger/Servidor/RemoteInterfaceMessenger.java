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

    //encriptar nomes
    public void connectUser(String user) throws RemoteException;
    public void disconnectUser(String user) throws RemoteException;

    public boolean hasMessages(String user) throws RemoteException;
    
    public Messages getSecretMessage(String user) throws RemoteException;

    public void setSecretMessage(byte[] msg, String user,String UserDestination) throws RemoteException;
    
//    public boolean hasFiles(String user)throws RemoteException;
//    
//    public byte[] getFile(String user)throws RemoteException;
//    
//    public void setFile(byte[] msg, String user) throws RemoteException;
    
    public ConcurrentHashMap getHash() throws RemoteException;
    
    public boolean hasUsers() throws RemoteException;
}
