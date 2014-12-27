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
    private String destination;

    public Messages(byte[] Message, String destination) {
        this.Message = Message;
        this.destination = destination;
    }

    
    
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public byte[] getMessage() {
        return Message;
    }

    public void setMessage(byte[] Message) {
        this.Message = Message;
    }
    
    
}
