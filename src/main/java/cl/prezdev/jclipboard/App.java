package cl.prezdev.jclipboard;

import cl.prezdev.jclipboard.model.JClipboard;

public class App {
    public static void main( String[] args ){
        JClipboard clip = new JClipboard(100, (x) -> System.out.println(x));

        clip.start();
    }
}
