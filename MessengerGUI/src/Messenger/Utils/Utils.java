/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messenger.Utils;

import java.awt.Image;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author pnlfe_000
 */
public class Utils {

    public static void writeText(final JTextPane jtPane, final String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = jtPane.getDocument();
                    doc.insertString(doc.getLength(), msg + "\n", null);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public static void writeImage(final JTextPane jtPane, final ImageIcon msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Image scaleImage = msg.getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT);
                msg.setImage(scaleImage);
                jtPane.insertIcon(msg);
            }
        });
    }

    public static File getFile() {

        // create a file chooser
        JFileChooser fileChooser = new JFileChooser();
        // show open file dialog
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
}
