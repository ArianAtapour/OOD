import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

/**
 * <p>This is the KeyController (KeyListener)</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

//FIX 90

public class KeyController extends KeyAdapter {

    //FIX 11 MAKING presentation variable final
    //private final Presentation presentation; //Commands are given to the presentation

    private SlideViewerComponent slideViewerComponent;

    public KeyController(SlideViewerComponent slideViewerComponent) {
        this.slideViewerComponent = slideViewerComponent;
    }


    //FIX 56 Adding @Override annotation above the method signature
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            //FIX 12 Switch statement saved lines of code by making it inline
            case KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER, '+' -> slideViewerComponent.nextSlide();
            case KeyEvent.VK_PAGE_UP, KeyEvent.VK_UP, '-' -> slideViewerComponent.prevSlide();
            case 'q', 'Q' -> System.exit(0);
            //FIX 57 EMPTY DEFAULT so I moved the comment inside of it so it is not empty
            default -> {
                //Should not be reached
            }
        }
    }
}
