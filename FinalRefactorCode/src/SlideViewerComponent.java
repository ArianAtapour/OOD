import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serial;
import javax.swing.JComponent;
import javax.swing.JFrame;


/**
 * <p>SlideViewerComponent is a graphical component that ca display Slides.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerComponent extends JComponent {

    //FIX 83: Data clump code smell fixed by implementing fields in another class SlideViewerStyleProprieties (SlideViewerComponent).
    //FIX 71 Making slide variable transient
    private transient Slide slide; //The current slide
    //FIX 31 labelFont variable set to null is redundant
    //FIX 32 labelFont variable is final so make it final
    private final Font labelFont; //The font for labels
    //FIX 33 presentation variable set to null is redundant
    //FIX 72 Making presentation variable transient
    private transient Presentation presentation; //The presentation
    //FIX 34 frame variable set to null is redundant
    //FIX 35 frame variable is final so make it final
    private final JFrame frame;

    //FIX 36 Annotate @Serial serialVersionUID variable
    @Serial
    private static final long serialVersionUID = 227L;

    public SlideViewerComponent(Presentation pres, JFrame frame) {
        setBackground(SlideViewerStyleProprieties.BGCOLOR);
        presentation = pres;
        labelFont = new Font(SlideViewerStyleProprieties.FONTNAME, SlideViewerStyleProprieties.FONTSTYLE, SlideViewerStyleProprieties.FONTHEIGHT);
        this.frame = frame;
    }

    //FIX 73 Adding @Override annotation
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Slide.WIDTH, Slide.HEIGHT);
    }

    public void update(Presentation presentation, Slide data) {
        if (data == null) {
            repaint();
            return;
        }
        this.presentation = presentation;
        this.slide = data;
        repaint();
        frame.setTitle(presentation.getTitle());
    }

    //Draw the slide
    //FIX 74 Adding @Override annotation
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(SlideViewerStyleProprieties.BGCOLOR);
        g.fillRect(0, 0, getSize().width, getSize().height);
        if (presentation.getSlideNumber() < 0 || slide == null) {
            return;
        }
        g.setFont(labelFont);
        g.setColor(SlideViewerStyleProprieties.COLOR);
        g.drawString("Slide " + (1 + presentation.getSlideNumber()) + " of " +
                presentation.getSize(), SlideViewerStyleProprieties.XPOS, SlideViewerStyleProprieties.YPOS);
        Rectangle area = new Rectangle(0, SlideViewerStyleProprieties.YPOS, getWidth(), (getHeight() - SlideViewerStyleProprieties.YPOS));
        slide.draw(g, area, this);
    }
}
