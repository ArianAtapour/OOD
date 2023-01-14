//FIX 52 - changing libraries to package. prefix

import java.awt.Frame;
import javax.swing.JOptionPane;

/**
 * The About-box for JabberPoint.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class AboutBox {
    //FIX 51 Adding a private constructor to hide the implicit public one
    private AboutBox() {
        //We never really use this constructor so if it is used to throw an exception
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    //FIX 3 - String builder to ease the amount of text inside the method
    private static final StringBuilder textDelimited = new StringBuilder();

    private static String text() {
        textDelimited.append("JabberPoint is a primitive slide-show program in Java(tm). It\n")
                .append("is freely copyable as long as you keep this notice and\n")
                .append("the splash screen intact.\n")
                .append("Copyright (c) 1995-1997 by Ian F. Darwin, ian@darwinsys.com.\n")
                .append("Adapted by Gert Florijn (version 1.1) and")
                .append("Sylvia Stuurman (version 1.2 and higher) for the Open")
                .append("University of the Netherlands, 2002 -- now.\n")
                .append("Author's version available from http://www.darwinsys.com/\n");
        return textDelimited.toString();
    }

    public static void show(Frame parent) {
        JOptionPane.showMessageDialog(parent,
                text(),
                "About JabberPoint",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
