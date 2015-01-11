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
    private byte[] source;
    private byte[] fileName;

    public Messages(byte[] Message, byte[] source) {
        this.Message = Message;
        this.source = source;
    }

    public Messages(byte[] Message, byte[] source, byte[] fileName) {
        this.Message = Message;
        this.source = source;
        this.fileName = fileName;
    }

    public byte[] getFileName() {
        return fileName;
    }

    public void setFileName(byte[] fileName) {
        this.fileName = fileName;
    }  
    
    public byte[] getSource() {
        return source;
    }

    public void setSource(byte[] source) {
        this.source = source;
    }

    public byte[] getMessage() {
        return Message;
    }

    public void setMessage(byte[] Message) {
        this.Message = Message;
    }   
        
}
