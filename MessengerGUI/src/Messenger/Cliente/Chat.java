//teste github

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messenger.Cliente;

import Messenger.Servidor.Messages;
import Messenger.Servidor.RemoteInterfaceMessenger;
import Messenger.Utils.Secrets;
import Messenger.Utils.Serializer;
import Messenger.Utils.Utils;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Key;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

//
/**
 *
 * @author Ricardo
 */
public class Chat extends javax.swing.JPanel implements Runnable {

    CopyOnWriteArrayList<JTextPane> chats;
    MainGUI mainGUI;
    Key sharedKey;
    RemoteInterfaceMessenger remote;
    DefaultListModel listModel;
    String REMOTE_NAME = "RemoteMsn";
    String host = Login.txtServerIP.getText();
    int port = Integer.parseInt(Login.txtServerPort.getText());
    String UserName;

    /**
     * Creates new form Chat
     */
    public Chat(MainGUI mainGUI) {
        chats = new CopyOnWriteArrayList<>();
        this.mainGUI = mainGUI;
        initComponents();
    }

    public void init() {
        UserName = Login.getTxtUsername().getText();
        txtLoginUserName.setText(UserName);

        try {

            //localizar o registry do servidor
            Registry registry = LocateRegistry.getRegistry(host, port);
            //obtem a referencia remota
            remote = (RemoteInterfaceMessenger) registry.lookup(REMOTE_NAME);
            //executr o servico
            Utils.writeText(txtStatus, " Messenger: ready");

            KeyPair myKeys = Secrets.generateKeyPair();
            byte[] key = remote.getSharedkey(myKeys.getPublic());
            key = Secrets.decrypt(key, myKeys.getPrivate());
            sharedKey = (Key) Serializer.toObject(key);

            Utils.writeText(txtStatus, " Messenger : Security ready");
            remote.connectUser(Login.txtUsername.getText());
            Utils.writeText(txtStatus, " Messeger : Autentication ready");
            //escutar o objecto remoto
            new Thread(this).start();

        } catch (Exception ex) {
            Utils.writeText(txtStatus, " Error");
        }
        update.start();
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
        listModel = new DefaultListModel();
        lstUsersOnline = new javax.swing.JList(listModel);
        txtMessage = new javax.swing.JTextField();
        btnSendMessage = new javax.swing.JButton();
        jTab = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtStatus = new javax.swing.JTextPane();
        btnChatTo = new javax.swing.JButton();
        btnAddToConversation = new javax.swing.JButton();
        btnFileSend = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnCloseConversation = new javax.swing.JButton();
        txtLoginUserName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        jScrollPane2.setViewportView(lstUsersOnline);

        btnSendMessage.setText("Send");
        btnSendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendMessageActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(txtStatus);

        jTab.addTab("Status", jScrollPane3);

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

        jLabel1.setText("UserName:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jTab)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addComponent(btnChatTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddToConversation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnFileSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCloseConversation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtLoginUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnSendMessage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLoginUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTab, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSendMessage)
                    .addComponent(btnLogout))
                .addContainerGap(87, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendMessageActionPerformed
        try {
            int i = jTab.getSelectedIndex();

            Utils.writeText(chats.get(i - 1), " Send : " + txtMessage.getText());

            byte[] data = Serializer.toByteArray(txtMessage.getText());
            data = Secrets.encrypt(data, sharedKey);

            remote.setSecretMessage(data, jTab.getTitleAt(i), UserName);
            txtMessage.setText("");
        } catch (Exception ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSendMessageActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        mainGUI.login();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnChatToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChatToActionPerformed
        String UserDestination = lstUsersOnline.getSelectedValue().toString();
        if (lstUsersOnline.getSelectedValue() != null) {
            newChatTo(UserDestination);
        }
    }//GEN-LAST:event_btnChatToActionPerformed


    private void btnAddToConversationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToConversationActionPerformed
        // TODO add your handling code here:
        System.out.println(chats.size());
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    protected javax.swing.JTabbedPane jTab;
    protected javax.swing.JList lstUsersOnline;
    private javax.swing.JTextField txtLoginUserName;
    protected javax.swing.JTextField txtMessage;
    private javax.swing.JTextPane txtStatus;
    // End of variables declaration//GEN-END:variables
 JTextArea tst = new JTextArea();

    @Override
    public void run() {
        //String User = Login.txtUsername.getText();
        while (true) {
            try {
                while (remote.hasMessages(UserName)) {
                    Messages m = remote.getSecretMessage(UserName);
                    byte[] data = m.getMessage();

                    data = Secrets.decrypt(data, sharedKey);
                    String msg = (String) Serializer.toObject(data);
                    //se for para a status escreve

                    tst.setName(m.getDestination());
                    if (!containsChatUser(m.getDestination()) && !m.getDestination().equals("txtStatus")) {
                        newChatTo(m.getDestination());
                    }

                    for (JTextPane jt : chats) {
                        if (jt.getName().equals(m.getDestination())) {
                            Utils.writeText(jt, " Get : " + msg);
                        }
                    }

                    if (m.getDestination().equals("txtStatus")) {
                        Utils.writeText(txtStatus, " Get : " + msg);
                    }

                }
                Thread.sleep(1000);
            } catch (Exception ex) {
                System.out.println("erro");
                Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean containsChatUser(String name) {
        for (JTextPane chat : chats) {
            if(chat.getName().equals(name))
                return true;
        }
        return false;
    }

    public void newChatTo(String UserDestination) {

        JTextPane j = new JTextPane();
        j.setName(UserDestination);
        chats.add(j);
        jTab.add(UserDestination, new JScrollPane(chats.get(chats.size() - 1)));

    }

    /**
     * Thread para update da lista de users
     */
    Thread update = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    if (remote.hasUsers()) {
                        for (Object users : remote.getHash().keySet()) {
                            if (!((String) users).equals(UserName)) {
                                if (!listModel.contains((String) users)) {
                                    listModel.addElement((String) users);
                                }
                            }
                        }
                    }
                    Thread.sleep(5000);
                }
            } catch (Exception ex) {
                Logger.getLogger(Chat.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    });

}