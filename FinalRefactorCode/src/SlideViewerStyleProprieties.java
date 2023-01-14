import java.awt.*;
import java.io.Serializable;

//FIX 83 Data clump code smell fixed by implementing fields in another class (SlideViewerComponent).

public class SlideViewerStyleProprieties implements Serializable {
    static final Color BGCOLOR = Color.white;
    static final Color COLOR = Color.black;
    static final String FONTNAME = "Dialog";
    static final int FONTSTYLE = Font.BOLD;
    static final int FONTHEIGHT = 10;
    static final int XPOS = 1100;
    static final int YPOS = 20;
}