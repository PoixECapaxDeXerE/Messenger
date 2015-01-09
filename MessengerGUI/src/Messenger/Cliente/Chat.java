/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messenger.Cliente;

import Messenger.Servidor.Messages;
import Messenger.Servidor.RemoteInterfaceMessenger;
import Messenger.Utils.RWserializable;
import Messenger.Utils.Secrets;
import Messenger.Utils.Serializer;
import Messenger.Utils.Utils;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.security.Key;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

/**
 *
 * @author pnlfe_000
 */
public class Chat extends javax.swing.JFrame implements Runnable {

    CopyOnWriteArrayList<JTextPane> chats;
    Key sharedKey;
    RemoteInterfaceMessenger remote;
    DefaultListModel listModel;
    Login log;
    String UserName;

    /**
     * Creates new form Chat17
     */
    public Chat(Login login) {
        initComponents();
        this.log = login;
        chats = new CopyOnWriteArrayList<>();

    }

    public void init() {

        try {
            this.sharedKey = log.sharedKey;
            this.remote = log.remote;
            this.UserName = log.getTxtUsername().getText();

            txtLoginUserName.setText(UserName);
            txtLoginUserName.setEditable(false);

            Utils.writeText(txtStatus, " Messenger: ready");

            Utils.writeText(txtStatus, " Messenger : Security ready");

            Utils.writeText(txtStatus, " Messeger : Autentication ready");
            //escutar o objecto remoto
            new Thread(this).start();

        } catch (Exception ex) {
            Utils.writeText(txtStatus, " Error");
        }
        update.start();
        setAvatar();

    }

    public void sendFile(int i, File file) {
        byte[] user = null;
        byte[] destination = null;
        byte[] fileName = null;
        try {
            //encripta os dados
            user = (Secrets.encrypt(Serializer.toByteArray(UserName), sharedKey));
            destination = (Secrets.encrypt(Serializer.toByteArray(jTab.getTitleAt(i)), sharedKey));
            fileName = (Secrets.encrypt(Serializer.toByteArray(file.getName()), sharedKey));
            //enviaFicheiro
            Utils.writeText(chats.get(i - 1), " Send : File:" + file.getName());
            Utils.writeText(chats.get(i - 1), " \n ");

            byte[] data = Serializer.toByteArray(RWserializable.readFile(file.getAbsolutePath()));
            data = Secrets.encrypt(data, sharedKey);
            //Assinar a mensagem
            remote.setFile(data, destination, user, fileName);
            txtMessage.setText("");
        } catch (Exception ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAvatar() {
        byte[] user = null;
        try {
            user = Secrets.encrypt(Serializer.toByteArray((UserName)), sharedKey);
            byte[] data = remote.getAvatar(user);
            //decripta as mensagens
            //  data = Secrets.decrypt(data, sharedKey);
            Object o = Serializer.toObject(data);

            ImageIcon icon = (ImageIcon) o;

            Utils.writeImage(paneAvatarChat, icon);
        } catch (Exception ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel2 = new javax.swing.JLabel();
        txtLoginUserName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        paneAvatarChat = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listModel = new DefaultListModel();
        lstUsersOnline = new javax.swing.JList(listModel);
        btnChatTo = new javax.swing.JButton();
        btnFileSend = new javax.swing.JButton();
        btnCloseConversation = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnSendMessage = new javax.swing.JButton();
        txtMessage = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTab = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtStatus = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("UserName:");

        jScrollPane1.setViewportView(paneAvatarChat);

        jLabel1.setText("Users Online:");

        jScrollPane2.setViewportView(lstUsersOnline);

        btnChatTo.setText("Chat!");
        btnChatTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChatToActionPerformed(evt);
            }
        });

        btnFileSend.setText("Send File...");
        btnFileSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileSendActionPerformed(evt);
            }
        });

        btnCloseConversation.setText("Close Conversation");
        btnCloseConversation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseConversationActionPerformed(evt);
            }
        });

        btnLogout.setText("Exit");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnSendMessage.setText("Send");
        btnSendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendMessageActionPerformed(evt);
            }
        });

        jLabel3.setText("Message to Send:");

        jScrollPane3.setViewportView(txtStatus);

        jTab.addTab("Status", jScrollPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel2)
                        .addGap(31, 31, 31)
                        .addComponent(txtLoginUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTab, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFileSend, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addComponent(btnChatTo, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addComponent(btnCloseConversation, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addComponent(btnSendMessage, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtLoginUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(5, 5, 5)
                                .addComponent(jTab, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnChatTo)
                                .addGap(18, 18, 18)
                                .addComponent(btnFileSend)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnCloseConversation)
                                .addGap(12, 12, 12)
                                .addComponent(btnLogout)
                                .addGap(45, 45, 45))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMessage))))
                    .addComponent(btnSendMessage))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnChatToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChatToActionPerformed
        String UserDestination = lstUsersOnline.getSelectedValue().toString();
        if (lstUsersOnline.getSelectedValue() != null && !chatExits(UserDestination)) {
            newChatTo(UserDestination);
        }
    }//GEN-LAST:event_btnChatToActionPerformed

    public boolean chatExits(String user) {
        for (JTextPane chat : chats) {
            if (chat.getName().equals(user)) {
                return true;
            }
        }
        return false;
    }


    private void btnFileSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileSendActionPerformed
        byte[] user = null;
        byte[] destination = null;
        if (jTab.getSelectedIndex() != 0) {
            try {
                int i = jTab.getSelectedIndex();
                //encripta os dados
                user = Secrets.encrypt(Serializer.toByteArray(UserName), sharedKey);
                destination = Secrets.encrypt(Serializer.toByteArray(jTab.getTitleAt(i)), sharedKey);
                //para ficheiros
                File file = Utils.getFile();
                String ext = Files.probeContentType(file.toPath());

                //se nao conhecer o tipo retorna null
                if (ext != null) {
                    //se for imagem
                    if (ext.contains("image")) {

                        ImageIcon icon = new ImageIcon(file.getAbsolutePath());

                        Utils.writeText(chats.get(i - 1), " Send : ");
                        Utils.writeImage(chats.get(i - 1), icon);
                        Utils.writeText(chats.get(i - 1), " \n ");
                        byte[] data = Serializer.toByteArray(new ImageIcon(file.getAbsolutePath()));
                        data = Secrets.encrypt(data, sharedKey);

                        remote.setSecretMessage(data, destination, user);
                        txtMessage.setText("");
                        //se nao for imagem e conhecer o tipo
                    } else {
                        sendFile(i, file);
                    }
                } else {
                    //se nao conhecer o tipo
                    sendFile(i, file);
                }
            } catch (Exception ex) {
                Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnFileSendActionPerformed

    private void btnCloseConversationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseConversationActionPerformed

        int i = jTab.getSelectedIndex();
        if (i != 0) {
            for (JTextPane chat : chats) {
                if (chat.getName().equals(jTab.getName())) {
                    chats.remove(chat);
                }
            }
            jTab.remove(i);
        }

    }//GEN-LAST:event_btnCloseConversationActionPerformed


    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        byte[] user = null;
        try {
            user = Secrets.encrypt(Serializer.toByteArray(UserName), sharedKey);
            remote.disconnectUser(user);
            WindowEvent close = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(close);

        } catch (Exception ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnSendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendMessageActionPerformed
        byte[] user = null;
        byte[] destination = null;
        if (jTab.getSelectedIndex() != 0) {
            try {
                int i = jTab.getSelectedIndex();
                //encripta os dados
                user = Secrets.encrypt(Serializer.toByteArray(UserName), sharedKey);
                destination = Secrets.encrypt(Serializer.toByteArray(jTab.getTitleAt(i)), sharedKey);
                //Envia
                Utils.writeText(chats.get(i - 1), " Send : " + txtMessage.getText());

                byte[] data = Serializer.toByteArray(txtMessage.getText());
                data = Secrets.encrypt(data, sharedKey);

                remote.setSecretMessage(data, destination, user);
                txtMessage.setText("");

            } catch (Exception ex) {
                Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSendMessageActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton btnChatTo;
    private javax.swing.JButton btnCloseConversation;
    private javax.swing.JButton btnFileSend;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnSendMessage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    protected javax.swing.JTabbedPane jTab;
    protected javax.swing.JList lstUsersOnline;
    private javax.swing.JTextPane paneAvatarChat;
    private javax.swing.JTextField txtLoginUserName;
    protected javax.swing.JTextField txtMessage;
    private javax.swing.JTextPane txtStatus;
    // End of variables declaration//GEN-END:variables

    JTextArea tst = new JTextArea();

    @Override
    public void run() {
        byte[] user = null;

        while (true) {
            try {
                //encripta os dados
                user = Secrets.encrypt(Serializer.toByteArray(UserName), sharedKey);
                //Envia
                while (remote.hasMessages(user)) {
                    Messages m = remote.getSecretMessage(user);
                    String name = (String) Serializer.toObject(Secrets.decrypt(m.getDestination(), sharedKey));
                    byte[] data = m.getMessage();
                    //decripta as mensagens
                    data = Secrets.decrypt(data, sharedKey);
                    Object o = Serializer.toObject(data);

                    //se nao existir a Janela do Chat entao cria
                    tst.setName(name);
                    if (!containsChatUser(name) && !name.equals("txtStatus")) {
                        newChatTo(name);
                    }

                    //se o conteudo das mensagens for uma String
                    if (o instanceof String) {
                        String msg = (String) o;
                        //se for para 1 utilizador
                        for (JTextPane jt : chats) {
                            if (jt.getName().equals(name)) {
                                Utils.writeText(jt, " Get : " + msg);
                            }
                        }
                        //se for para o Status
                        if (name.equals("txtStatus")) {
                            Utils.writeText(txtStatus, " Get : " + msg);
                        }
                        //se for uma imagem mostra
                    } else if (o instanceof ImageIcon) {
                        //imagem para mostrar
                        ImageIcon icon = (ImageIcon) o;
                        //se for para 1 utilizador
                        for (JTextPane jt : chats) {
                            if (jt.getName().equals(name)) {
                                Utils.writeText(jt, " Get : ");
                                Utils.writeImage(jt, icon);
                                Utils.writeText(jt, " \n ");
                            }
                        }
                    } else {
                        byte[] file = (byte[]) o;

                        String fileName = (String) Serializer.toObject(Secrets.decrypt(m.getFileName(), sharedKey));

                        RWserializable.writeFile(data, fileName);
                        for (JTextPane jt : chats) {
                            if (jt.getName().equals(name)) {
                                Utils.writeText(jt, " Get : File " + fileName);
                                Utils.writeText(jt, " \n ");
                            }
                        }
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
            if (chat.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Cria uma nova Tab
     *
     * @param UserDestination
     */
    public void newChatTo(String UserDestination) {

        JTextPane j = new JTextPane();
        j.setEditable(false);
        j.setName(UserDestination);
        chats.add(j);
        jTab.add(UserDestination, new JScrollPane(chats.get(chats.size() - 1)));
        jTab.setSelectedIndex(jTab.getTabCount() - 1);
        jTab.setName(UserDestination);
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
                        ConcurrentHashMap hash = (ConcurrentHashMap) Serializer.toObject(Secrets.decrypt(remote.getHash(), sharedKey));
                        for (Object users : hash.keySet()) {
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
