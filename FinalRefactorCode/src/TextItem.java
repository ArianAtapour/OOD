import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.List;
//FIX 42 Removing unused library Iterator because of for loop change which integrates it inside
import java.util.ArrayList;

/**
 * <p>A text item.</p>
 * <p>A text item has drawing capabilities.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem extends SlideItem {
    //FIX 38 Variable text is final so make it so
    private final String text;

    //FIX 39 DEAD CODE EMPTYTEXT variable is never used

    //A textitem of int level with text string
    public TextItem(int level, String string) {
        super(level);
        text = string;
    }

    //FIX 8 - DEAD CODE TextItem constructor is never used

    //Returns the text
    public String getText() {
        return text == null ? "" : text;
    }

    //Returns the AttributedString for the Item
    public AttributedString getAttributedString(Style style, float scale) {
        AttributedString attrStr = new AttributedString(getText());
        attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, text.length());
        return attrStr;
    }

    //Returns the bounding box of an Item
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer,
                                    float scale, Style myStyle) {
        List<TextLayout> layouts = getLayouts(g, myStyle, scale);
        int xsize = 0, ysize = (int) (myStyle.leading * scale);
        //FIX 40 Replacing while loop with for loop for better readability and functionality
        for (TextLayout layout : layouts) {
            Rectangle2D bounds = layout.getBounds();
            if (bounds.getWidth() > xsize) {
                xsize = (int) bounds.getWidth();
            }
            if (bounds.getHeight() > 0) {
                ysize += bounds.getHeight();
            }
            ysize += layout.getLeading() + layout.getDescent();
        }
        return new Rectangle((int) (myStyle.indent * scale), 0, xsize, ysize);
    }

    //Draws the item
    public void draw(int x, int y, float scale, Graphics g,
                     Style myStyle, ImageObserver o) {
        if (text == null || text.length() == 0) {
            return;
        }
        List<TextLayout> layouts = getLayouts(g, myStyle, scale);
        Point pen = new Point(x + (int) (myStyle.indent * scale),
                y + (int) (myStyle.leading * scale));
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(myStyle.color);
        //FIX 41 Replacing while loop with for loop for better readability and functionality
        for (TextLayout layout : layouts) {
            pen.y += layout.getAscent();
            layout.draw(g2d, pen.x, pen.y);
            pen.y += layout.getDescent();
        }
    }

    private List<TextLayout> getLayouts(Graphics g, Style s, float scale) {
        //FIX 43 To save verbose removing TextLayout argumanent and leaving diamond <>
        List<TextLayout> layouts = new ArrayList<>();
        AttributedString attrStr = getAttributedString(s, scale);
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
        float wrappingWidth = (Slide.WIDTH - s.indent) * scale;
        while (measurer.getPosition() < getText().length()) {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            layouts.add(layout);
        }
        return layouts;
    }

    public String toString() {
        return "TextItem[" + getLevel() + "," + getText() + "]";
    }
}
