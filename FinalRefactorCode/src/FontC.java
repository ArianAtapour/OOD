import java.awt.*;
import java.text.AttributedCharacterIterator;
import java.util.Map;

//FIX 87 For adding custom fonts created a new class

public class FontC extends Font {
    private Color fontColor;
    private String fontN;
    private int fontS;
    private int fontW;

    public FontC(Color fontColor,String fontName, int fontStyle, int fontSize) {
        super(fontName, fontStyle, fontSize);
        this.fontColor = fontColor;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public String getFontN() {
        return fontN;
    }

    public void setFontN(String fontN) {
        this.fontN = fontN;
    }

    public int getFontS() {
        return fontS;
    }

    public void setFontS(int fontS) {
        this.fontS = fontS;
    }

    public int getFontW() {
        return fontW;
    }

    public void setFontW(int fontW) {
        this.fontW = fontW;
    }
}
