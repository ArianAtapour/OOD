import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;

//FIX 89 Moving presentation methods for moving slides to here and implementin the FontCreator interface

/**
 * <p>SlideViewerComponent is a graphical component that ca display Slides.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerComponent extends JComponent implements FontCreator{

    //FIX 83: Data clump code smell fixed by implementing fields in another class SlideViewerStyleProprieties (SlideViewerComponent).
    //FIX 71 Making slide variable transient
    private transient Slide slide; //The current slide
    //FIX 31 labelFont variable set to null is redundant
    //FIX 32 labelFont variable is final so make it final
    //private final Font labelFont; //The font for labels
    //FIX 33 presentation variable set to null is redundant
    //FIX 72 Making presentation variable transient
    private Presentation presentation; //The presentation
    //FIX 34 frame variable set to null is redundant
    //FIX 35 frame variable is final so make it final
    private final JFrame frame;

    private FontC fontCustom;

    //FIX 36 Annotate @Serial serialVersionUID variable
    @Serial
    private static final long serialVersionUID = 227L;

    public SlideViewerComponent(Presentation pres, JFrame frame) {
        setBackground(SlideViewerStyleProprieties.BGCOLOR);
        this.presentation = pres;
        //labelFont = new Font(SlideViewerStyleProprieties.FONTNAME, SlideViewerStyleProprieties.FONTSTYLE, SlideViewerStyleProprieties.FONTHEIGHT);
        fontCustom = creatorFont(Color.BLACK, "Dialog", Font.BOLD, 10);
        this.frame = frame;
    }

        //Taken from Presentation class pre-refactoring
    public void setSlideNumber(int number) {
        this.presentation.setCurrentSlideNumber(number);
        this.update(presentation, this.presentation.getCurrentSlide());
    }

    //Navigate to the previous slide unless we are at the first slide
    public void prevSlide() {
        if (presentation.getSlideNumber() > 0) {
            setSlideNumber(presentation.getSlideNumber() - 1);
        }
    }

    //Navigate to the next slide unless we are at the last slide
    public void nextSlide() {
        if (presentation.getSlideNumber() < (presentation.getSize() - 1)) {
            setSlideNumber(presentation.getSlideNumber() + 1);
        }
    }

    public Presentation getPresentation(){
        return this.presentation;
    }

    //Remove the presentation
    void clear() {
        //FIX 25 Slide argument from ArrayList can be replaced with <> because of less verbose
        presentation.getShowList().clear();
        setSlideNumber(-1);
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
        g.setFont(fontCustom);
        g.setColor(fontCustom.getFontColor());
        g.drawString("Slide " + (1 + presentation.getSlideNumber()) + " of " +
                presentation.getSize(), SlideViewerStyleProprieties.XPOS, SlideViewerStyleProprieties.YPOS);
        Rectangle area = new Rectangle(0, SlideViewerStyleProprieties.YPOS, getWidth(), (getHeight() - SlideViewerStyleProprieties.YPOS));
        slide.draw(g, area, this);
    }

    @Override
    public FontC creatorFont(Color fontColor, String fontName, int fontStyle, int fontSize) {
        fontCustom = new FontC(fontColor, fontName, fontStyle, fontSize);
        return fontCustom;
    }
}
