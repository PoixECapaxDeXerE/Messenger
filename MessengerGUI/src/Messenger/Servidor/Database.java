/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messenger.Servidor;

import Messenger.Utils.RWserializable;
import Messenger.Utils.Secrets;
import java.security.Key;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pnlfe_000
 */
public class Database {

    public Database() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/files/users.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public static boolean insert(String User, String Pass, String Q, String A, byte[] avatar) {
        try {
            Connection c = null;
            Statement stmt = null;
            int id = 0;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:src/files/users.db");
                c.setAutoCommit(false);
                System.out.println("Opened database successfully");
                //vai buscar o valor maximo do id
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT max(id) as maxID FROM USERS;");
                id = rs.getInt("maxID");
                id++;
                //insere na base de dados
                stmt = c.createStatement();
                String sql = "INSERT INTO USERS (ID,NAME,PASS,QUESTION,ANSWER,AVATAR) "
                        + "VALUES (" + id + ", '" + User + "', '" + Pass + "','" + Q + "','" + A + "','avatar" + User + "' );";
                stmt.executeUpdate(sql);

                stmt.close();
                c.commit();
                c.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            System.out.println("Records created successfully");
            Key key = Secrets.getKeyFromFile();
            Secrets.encryptFileToSave(avatar, "avatar" + User, key);
            //RWserializable.writeFile(avatar, );
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void create() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/files/users.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE USERS "
                    + "(ID INT PRIMARY KEY     NOT NULL,"
                    + " NAME           TEXT    NOT NULL, "
                    + " PASS           TEXT    NOT NULL, "
                    + " QUESTION       TEXT    NOT NULL,"
                    + " ANSWER         TEXT    NOT NULL,"
                    + " AVATAR         TEXT    NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public static byte[] getAvatar(String user) {
        byte[] AVATAR = null;
        String str = null;
        try {

            Connection c = null;
            Statement stmt = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:src/files/users.db");
                c.setAutoCommit(false);
                System.out.println("Opened database successfully");

                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT AVATAR FROM USERS Where NAME ='" + user + "';");

                str = rs.getString("AVATAR");

                rs.close();
                stmt.close();
                c.close();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            System.out.println("Operation done successfully");
            Key key = Secrets.getKeyFromFile();
            AVATAR = Secrets.decryptFileToSave("src/Files/" + str, key);

        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return AVATAR;
    }

    public static void select(JTable table) {
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/files/users.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("NAME");
                String pass = rs.getString("PASS");
                String quest = rs.getString("QUESTION");
                String ans = rs.getString("ANSWER");
                System.out.println("ID = " + id);
                System.out.println("NAME " + name);
                System.out.println("PASS = " + pass);
                System.out.println("QUESTION = " + quest);
                System.out.println("ANSWER = " + ans);
                System.out.println();
                Object aux[] = new Object[4];
                aux[0] = name;
                aux[1] = pass;
                aux[2] = quest;
                aux[3] = ans;
                m.addRow(aux);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    public static boolean userExists(String User) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/files/users.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS where NAME = '" + User + "' ;");
            if (rs.getBoolean("NAME")) {

                rs.close();
                stmt.close();
                c.close();

                return false;
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (ClassNotFoundException | SQLException e) {

            try {
                stmt.close();
                c.close();
                return false;
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Operation done successfully");
        return true;
    }

    public static boolean correctAnswer(String Name, String Answer) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/files/users.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS where NAME = '" + Name + "' ;");

            String Ans = rs.getString("ANSWER");

            rs.close();
            stmt.close();
            c.close();
            if (Ans.equals(Answer)) {
                return true;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return false;
    }

    public static String getPass(String user) {
        String Pass = null;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/files/users.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS where NAME = '" + user + "' ;");

            Pass = rs.getString("PASS");

            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return Pass;
    }

    public static String getQuestion(String user) {
        String question = null;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/files/users.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS where NAME = '" + user + "' ;");

            question = rs.getString("QUESTION");

            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return question;
    }

    public static boolean login(String user, String PassClient) {
        String Pass = null;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/files/users.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS where NAME = '" + user + "' ;");

            Pass = rs.getString("PASS");

            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        if (Pass.equals(PassClient)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        try {

            byte[] as = RWserializable.readFile("icon.png");

            //Database db = new Database();
            Database.create();
            // Database.select();
            Database.insert("Pedro3", "1234", "ola", "ola", as);
//        db.insert("Pedro1", "1234", "ola", "ola");
//        db.insert("Pedro2", "1234", "ola", "ola");
            System.out.println(Database.userExists("Pedro3"));
            System.out.println(Database.getPass("Pedro3"));
            System.out.println(Database.correctAnswer("Pedro3", "ola1"));
            System.out.println(Database.login("Pedro3", "12345"));
            System.out.println(Database.getAvatar("Pedro3"));
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void changeKey() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/files/users.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS;");
            Key key = Secrets.getKeyFromFile();
            byte[] file1 = Secrets.decryptFileToSave("src/Files/" + "Ricardo", key);
            // Key key = Secrets.getKeyFromFile();
            Secrets.GenerateKEYSaveFile();
            Key newKey = Secrets.getKeyFromFile();

            while (rs.next()) {

                String name = rs.getString("AVATAR");

                byte[] file = Secrets.decryptFileToSave("src/Files/" + name, key);
                Secrets.encryptFileToSave(file, name, newKey);

            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

}
