import java.awt.*;

//FIX 88 Added the font creator interface to create the font

public interface FontCreator {
    FontC creatorFont(Color color, String fontName, int fontStyle, int fontSize);
}
