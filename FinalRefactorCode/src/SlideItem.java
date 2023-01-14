import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 * <p>The abstract class for items on a slide.<p>
 * <p>All SlideItems have drawing capabilities.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public abstract class SlideItem {
    //FIX 29 level variable set to 0 is redundant since you initialise the variable already
    //FIX 30 level variable is final so change it to final
    private final int level; //The level of the SlideItem

    //FIX 70 Changing the visibility of the constructor from public to protected
    protected SlideItem(int lev) {
        level = lev;
    }

    //FIX 7 - DEAD CODE SlideItem constructor never used

    //Returns the level
    public int getLevel() {
        return level;
    }

    //Returns the bounding box
    public abstract Rectangle getBoundingBox(Graphics g,
                                             ImageObserver observer, float scale, Style style);

    //Draws the item
    public abstract void draw(int x, int y, float scale,
                              Graphics g, Style style, ImageObserver observer);
}
