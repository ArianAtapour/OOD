import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.io.Serial;
import javax.swing.JFrame;

/**
 * <p>The applicatiewindow for a slideviewcomponent</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerFrame extends JFrame {
    //FIX 37 @Serial annotation for serialVersionUID variable
    @Serial
    private static final long serialVersionUID = 3227L;

    private static final String JABTITLE = "Jabberpoint 1.6 - OU";
    //FIX 76 Reordering modifiers
    public static final int WIDTH = 1200;

    //FIX 77 Reordering modifiers
    public static final int HEIGHT = 800;

    private SlideViewerComponent slideViewerComponent;

    public SlideViewerFrame(String title, Presentation presentation) {
        super(title);
        this.slideViewerComponent = new SlideViewerComponent(presentation, this);
        //presentation.setShowView(slideViewerComponent);
        setupWindow(slideViewerComponent, slideViewerComponent.getPresentation());
    }

    public SlideViewerComponent getSlideViewerComponent(){
        return this.slideViewerComponent;
    }

    //Setup the GUI
    public void setupWindow(SlideViewerComponent
                                    slideViewerComponent, Presentation presentation) {
        setTitle(JABTITLE);
        addWindowListener(new WindowAdapter() {
            //FIX 75 Adding @Override annotation
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        getContentPane().add(slideViewerComponent);
        addKeyListener(new KeyController(slideViewerComponent)); //Add a controller
        setMenuBar(new MenuController(this, slideViewerComponent));    //Add another controller
        setSize(new Dimension(WIDTH, HEIGHT)); //Same sizes a slide has
        setVisible(true);
    }
}
