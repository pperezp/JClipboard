package jclipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jclipboard.listeners.DataCopiedListener;

public class JClipboard extends Thread {

    private List<DataCopiedListener> listeners;
    private List<String> textList;
    private Clipboard clipboard;
    private Transferable dato;
    private DataFlavor flavor;
    private String text;
    private long milis;

    public JClipboard(long milis, DataCopiedListener listener) {
        try {
            listeners = new ArrayList<>();
            textList = new ArrayList<>();
            listeners.add(listener);
            this.milis = milis;
            flavor = new DataFlavor("application/x-java-serialized-object; class=java.lang.String");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JClipboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addDataCopiedListener(DataCopiedListener listener) {
        listeners.add(listener);
    }

    @Override
    public void run() {
        while (true) {
            try {
                clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                dato = clipboard.getContents(new Object());

                if (dato != null) {
                    if (dato.isDataFlavorSupported(flavor)) {//si lo copiado es String
                        text = (String) dato.getTransferData(flavor);

                        if (!textList.contains(text)) {
                            listeners.forEach((lis) -> {
                                lis.stringCopied(text);
                            });
                            
                            textList.add(text);
                        }

                    }
                }

                Thread.sleep(milis);
            } catch (InterruptedException ex) {
                Logger.getLogger(JClipboard.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedFlavorException ex) {
                Logger.getLogger(JClipboard.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JClipboard.class.getName()).log(Level.SEVERE, null, ex);
            } catch(IllegalStateException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

}
