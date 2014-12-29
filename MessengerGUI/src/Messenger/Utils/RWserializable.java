/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messenger.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author pnlfe_000
 */
public class RWserializable {

    /**
     * Escreve num ficheiro.
     *
     * @param message - Conteudo a escrever
     * @param fileName - Nome do ficheiro
     */
    public static void writeArryOBJ(Object[] message, String fileName) {
        //escrever a chave de encriptacao no ficheiro .key
        ObjectOutputStream kos;
        try {
            kos = new ObjectOutputStream(new FileOutputStream(fileName));
            kos.writeObject(message);
            kos.close();
        } catch (IOException ex) {
            System.out.println("Erro na escrita do objecto");
        }
    }

    public static void write(Object message, String fileName) {
        //escrever a chave de encriptacao no ficheiro .key
        ObjectOutputStream kos;

        try {
            kos = new ObjectOutputStream(new FileOutputStream(fileName));
            kos.writeObject(message);

            kos.close();
        } catch (IOException ex) {
            System.out.println("Erro na escrita da chave");
        }

    }

    /**
     * Le um ficheiro.
     *
     * @param fileName - nome do ficheiro
     * @throws Exception
     */
    public static byte[] readFile(String fileName) throws Exception {
        File myFile = new File(fileName);
        FileInputStream fis = new FileInputStream(myFile);
        BufferedInputStream bis = new BufferedInputStream(fis);

        
        byte[] mybytearray = new byte[(int) myFile.length()];
        fis = new FileInputStream(myFile);
        bis.read(mybytearray, 0, mybytearray.length);
        bis.close();
        fis.close();
        return mybytearray;
    }

    /**
     * Le um ficheiro.
     *
     * @param fileName - nome do ficheiro
     * @throws Exception
     */
    public static void writeFile(byte[] data, String fileName) throws Exception {
        FileOutputStream fos = new FileOutputStream("src/Files/"+fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bos.write(data, 0, data.length);
        bos.flush();
        fos.close();
        bos.close();
    }

}
