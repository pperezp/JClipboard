package test;

import jclipboard.JClipboard;

public class Test {
    public static void main(String[] args) {
        JClipboard clip = new JClipboard(50, (x) -> System.out.println(x));
        
        clip.start();
    }
    
}
