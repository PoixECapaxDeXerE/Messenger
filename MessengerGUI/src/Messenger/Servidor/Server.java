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


import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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
public class Server {

    public static final String objectName = "RemoteMsn";
    public static final int port = 10010;

    public static void main(String[] args) {
        try {
            //cria um objecto remoto
            RemoteObjectMessenger remote = new RemoteObjectMessenger();
            //cria o stub
            UnicastRemoteObject.exportObject(remote, 0);
            //cria a registo
            Registry registry = LocateRegistry.createRegistry(port);
            //disponibiliza o objecto remoto
            registry.rebind(objectName, remote);
            //informacao do objecto
            System.out.println("Endereco                  : " 
                    + InetAddress.getLocalHost().toString());
            System.out.println("Porto                     : " + port);
            System.out.println("Objecto remoto disponivel : " + objectName);

        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
