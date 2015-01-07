/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messenger.Servidor;

import java.io.Serializable;

/**
 *
 * @author pnlfe_000
 */
public class Messages implements Serializable {
    
    private byte[] Message;
    private byte[] destination;
    private byte[] fileName;

    public Messages(byte[] Message, byte[] destination) {
        this.Message = Message;
        this.destination = destination;
    }

    public Messages(byte[] Message, byte[] destination, byte[] fileName) {
        this.Message = Message;
        this.destination = destination;
        this.fileName = fileName;
    }

    public byte[] getFileName() {
        return fileName;
    }

    public void setFileName(byte[] fileName) {
        this.fileName = fileName;
    }  
    
    public byte[] getDestination() {
        return destination;
    }

    public void setDestination(byte[] destination) {
        this.destination = destination;
    }

    public byte[] getMessage() {
        return Message;
    }

    public void setMessage(byte[] Message) {
        this.Message = Message;
    }   
        
}
