/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messengergui;

import Messenger.Servidor.RemoteInterfaceMessenger;
import Messenger.Utils.Secrets;
import Messenger.Utils.Serializer;
import Messenger.Utils.Utils;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Key;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Ricardo
 */
public class Chat extends javax.swing.JPanel implements Runnable {

    ArrayList <JTextArea> chats = new ArrayList <JTextArea>();
    
    int arrSize;
    MainGUI mainGUI;
    /**
     * Creates new form Chat
     */
    public Chat(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        initComponents();
    }
    
    public void connect(){
        try {
            //objecto que e remoto

            //localizar o registry do servidor
            Registry registry = LocateRegistry.getRegistry(Login.txtServerIP.getText(), Integer.parseInt(Login.txtServerPort.getText()));
            //obtem a referencia remota
            mainGUI.remote = (RemoteInterfaceMessenger) registry.lookup(MainGUI.REMOTE_NAME);
            //executr o servico
            Utils.writeText(jtxtStatus, " Messenger: ready");

            KeyPair myKeys = Secrets.generateKeyPair();
            byte[] key = mainGUI.remote.getSharedkey(myKeys.getPublic());
            key = Secrets.decrypt(key, myKeys.getPrivate());
            mainGUI.sharedKey = (Key) Serializer.toObject(key);

            Utils.writeText(jtxtStatus, " Messenger : Security ready");
            mainGUI.remote.connectUser(Login.txtUsername.getText());
            Utils.writeText(jtxtStatus, " Messeger : Autentication ready");
            //escutar o objecto remoto
            new Thread(this).start();
            
        } catch (Exception ex) {
            Utils.writeText(jtxtStatus, " Error");
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        lstUsersOnline = new javax.swing.JList();
        txtMessage = new javax.swing.JTextField();
        btnSendMessage = new javax.swing.JButton();
        jTab = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtxtStatus = new javax.swing.JTextArea();
        btnChatTo = new javax.swing.JButton();
        btnAddToConversation = new javax.swing.JButton();
        btnFileSend = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnCloseConversation = new javax.swing.JButton();

        lstUsersOnline.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "User1", "User2"};
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(lstUsersOnline);

        btnSendMessage.setText("Send");
        btnSendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendMessageActionPerformed(evt);
            }
        });

        jtxtStatus.setColumns(20);
        jtxtStatus.setRows(5);
        jScrollPane1.setViewportView(jtxtStatus);

        jTab.addTab("Status", jScrollPane1);

        btnChatTo.setText("Chat!");
        btnChatTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChatToActionPerformed(evt);
            }
        });

        btnAddToConversation.setText("Add user to chat...");
        btnAddToConversation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToConversationActionPerformed(evt);
            }
        });

        btnFileSend.setText("Send File...");
        btnFileSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileSendActionPerformed(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnCloseConversation.setText("Close Conversation");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSendMessage))
                    .addComponent(jTab))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(btnChatTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddToConversation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFileSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCloseConversation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnChatTo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddToConversation)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFileSend)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCloseConversation))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTab, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSendMessage)
                    .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogout))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendMessageActionPerformed
        // TODO add your handling code here:
        try {
            int i = jTab.getSelectedIndex();
            
            Utils.writeText(chats.get(i-1), " Send : " + txtMessage.getText());
/*
            byte[] data = Serializer.toByteArray(txtMessage.getText());
            data = Secrets.encrypt(data, Login.sharedKey);

            Login.remote.setSecretMessage(data, jTab.getName());
            txtMessage.setText("");*/
        } catch (Exception ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSendMessageActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        mainGUI.login();        // TODO add your handling code here:
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnChatToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChatToActionPerformed
        // TODO add your handling code here:
        chats.add(new JTextArea());
        arrSize = chats.size();
        jTab.add(lstUsersOnline.getSelectedValue().toString(), new JScrollPane(chats.get(arrSize-1)));
                
        
        //jTab.add(lstUsersOnline.getSelectedValue().toString(), jScrollArr.);
        
    }//GEN-LAST:event_btnChatToActionPerformed

    private void btnAddToConversationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToConversationActionPerformed
        // TODO add your handling code here:
        System.out.println(chats.size());
        //JTextArea
    }//GEN-LAST:event_btnAddToConversationActionPerformed

    private void btnFileSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileSendActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnFileSendActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddToConversation;
    protected javax.swing.JButton btnChatTo;
    private javax.swing.JButton btnCloseConversation;
    private javax.swing.JButton btnFileSend;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnSendMessage;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    protected javax.swing.JTabbedPane jTab;
    protected static javax.swing.JTextArea jtxtStatus;
    protected javax.swing.JList lstUsersOnline;
    protected javax.swing.JTextField txtMessage;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
       String User = Login.txtUsername.getText();
        try {
                while (MainGUI.remote.hasMessages(User)) {
                    System.out.println("0");
                    byte[] data = MainGUI.remote.getSecretMessage(User);
                    System.out.println("1");
                    data = Secrets.decrypt(data, MainGUI.sharedKey);
                    System.out.println("2");
                    String msg = (String) Serializer.toObject(data);
                    System.out.println("3");
                    Utils.writeText(jtxtStatus, " Get : " + msg);
                    System.out.println("4 " + msg);
                }
                Thread.sleep(1000);
            } catch (Exception ex) {
                System.out.println("erro");
                Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
            }

        
    }
    

}
