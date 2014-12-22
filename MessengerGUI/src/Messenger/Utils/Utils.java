/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messenger.Utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author pnlfe_000
 */
public class Utils {

    public static void writeText(final JTextArea jtarea, final String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                jtarea.append(msg + "\n");
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
