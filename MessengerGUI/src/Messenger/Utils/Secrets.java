/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messenger.Utils;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author pnlfe_000
 */
public class Secrets {

    public static byte[] decrypt(byte[] data, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            //configurar o objecto para cifrar
            cipher.init(cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception ex) {
            Logger.getLogger(Secrets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static byte[] encrypt(byte[] data, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            //configurar o objecto para cifrar
            cipher.init(cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception ex) {
            Logger.getLogger(Secrets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Key generateKey(String algoritmo) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(algoritmo);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Secrets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Secrets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

//    public String getHash(String msg){
//        try {
//            //objecto da integridade
//            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//            //fornecedor de dados ao objecto
//            messageDigest.update(msg.getBytes("UTF16"));
//            //calcular o valor da hash
//            byte[] hash = messageDigest.digest();
//            return "";
//        } catch (Exception ex) {
//            Logger.getLogger(Secrets.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
    public static void GenerateKEYSaveFile() {
        try {
            String algoritmo = "AES";

            //--- Gera chave e objecto para cifrar --------------------------
            Key key = generateKey(algoritmo);
            //guarda a chave para cifrar
            RWserializable.write(key, "src/Files/key" + ".key");

        } catch (Exception ex) {
            Logger.getLogger(Secrets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Key getKeyFromFile() {
        Key key = null;
        try {
            //--- Gera chave e objecto para cifrar --------------------------
            //guarda a chave para cifrar
            byte[] data = RWserializable.readFile("src/Files/key" + ".key");
            Object o = Serializer.toObject(data);
            key = (Key) o;
        } catch (Exception ex) {
            Logger.getLogger(Secrets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return key;
    }

    public static void encryptFileToSave(byte[] file, String FileName, Key key) {

        try {
            //cria um objecto Chipher
            Cipher chipher = Cipher.getInstance(key.getAlgorithm());
            //------Codificar------------------------------------------------
            //configura o objecto a cifrar
            chipher.init(chipher.ENCRYPT_MODE, key);
            //codifica a mensagem
            file = chipher.doFinal(file);

            RWserializable.writeFile(file, FileName);
            
        } catch (Exception ex) {
            Logger.getLogger(Secrets.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static byte[] decryptFileToSave(String FileName,Key key) {
        byte[] file = null;
        try {
            file = RWserializable.readFile(FileName);
            //cria um objecto Chipher
            Cipher chipher = Cipher.getInstance(key.getAlgorithm());
            //------Codificar------------------------------------------------
            //configura o objecto a cifrar
            chipher.init(chipher.DECRYPT_MODE, key);
            //codifica a mensagem
            file = chipher.doFinal(file);
        } catch (Exception ex) {
            Logger.getLogger(Secrets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

//    public static void main(String[] args) {
//        Secrets.GenerateKEYSaveFile();
//    }
}
