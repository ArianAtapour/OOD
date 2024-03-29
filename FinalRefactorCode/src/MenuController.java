import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
//FIX 25 Removing unused import statements ActionListener and ActionEvent
import java.io.IOException;
import java.io.Serial;

import javax.swing.JOptionPane;

//FIX 91

/**
 * <p>The controller for the menu</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {

    //FIX 13 Making parentF variable final
    //FIX 58 changing parent name to something else (parentF) since it is used a field with the same name in MenuController
    private final Frame parentF; //The frame, only used as parentF for the Dialogs
    //FIX 14 Making presentation variable final

    //FIX 59 Making presentation variable transient
    //private final transient Presentation presentation; //Commands are given to the presentation

    //FIX 15 Using @Serial annotation for serialVersionUID to give compile-time assurance

    private SlideViewerComponent slideViewerComponent;
    @Serial
    private static final long serialVersionUID = 227L;

    private MenuItem menuItem;

    private final Menu fileMenu = new Menu(MenuButtons.FILE);

    private final Menu viewMenu = new Menu(MenuButtons.VIEW);

    private final Menu helpMenu = new Menu(MenuButtons.HELP);

    public MenuController(Frame frame, SlideViewerComponent slideViewerComponent) {
        this.parentF = frame;
        //presentation = pres;
        this.slideViewerComponent = slideViewerComponent;
        //FIX 60-65 Extracted the assignment out of the expression (filemMenu.add)
        //FIX 84 Extracted methods from constructor into separate methods
        add(fileMenu);
        openPresentation();
        newPresentation();
        savePresentation();
        exitPresentation();
        nextSlide();
        previousSlide();
        goToSlide();
        help();
        setHelpMenu(helpMenu);        //Needed for portability (Motif, etc.).
    }

    private void openPresentation() {
        menuItem = getMenuItem(fileMenu, MenuButtons.OPEN);
        //FIX 16 - 23 Used lambda expression for ActionListener to enable functional programming and improve functionality
        menuItem.addActionListener(actionEvent -> {
            slideViewerComponent.clear();
            AccessorLoadFile xmlAccessorLoadFile = new xmlAccessorLoadFile();
            try {
                xmlAccessorLoadFile.loadFile(slideViewerComponent.getPresentation(), MenuButtons.TESTFILE);
                slideViewerComponent.setSlideNumber(0);
            } catch (IOException exc) {
                JOptionPane.showMessageDialog(parentF, MenuButtons.IOEX + exc,
                        MenuButtons.LOADERR, JOptionPane.ERROR_MESSAGE);
            }
            parentF.repaint();
        });
    }

    private void newPresentation() {
        menuItem = getMenuItem(fileMenu, MenuButtons.NEW);
        menuItem.addActionListener(actionEvent -> {
            slideViewerComponent.clear();
            parentF.repaint();
        });
    }

    private void savePresentation() {
        menuItem = getMenuItem(fileMenu, MenuButtons.SAVE);
        menuItem.addActionListener(e -> {
            AccessorSaveFile xmlAccessorSaveFile = new xmlAccessorSaveFile();
            try {
                xmlAccessorSaveFile.saveFile(slideViewerComponent.getPresentation(), MenuButtons.SAVEFILE);
            } catch (IOException exc) {
                JOptionPane.showMessageDialog(parentF, MenuButtons.IOEX + exc,
                        MenuButtons.SAVEERR, JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void exitPresentation() {
        fileMenu.addSeparator();
        menuItem = getMenuItem(fileMenu, MenuButtons.EXIT);
        menuItem.addActionListener(actionEvent -> slideViewerComponent.getPresentation().exit(0));
        add(fileMenu);
    }

    private void nextSlide() {
        menuItem = getMenuItem(viewMenu, MenuButtons.NEXT);
        menuItem.addActionListener(actionEvent -> slideViewerComponent.nextSlide());
    }

    private void previousSlide() {
        menuItem = getMenuItem(viewMenu, MenuButtons.PREV);
        menuItem.addActionListener(actionEvent -> slideViewerComponent.prevSlide());
    }

    private void goToSlide() {
        //FIX: GO TO > LIMIT ON SLIDE COUNTER DONE!
        menuItem = getMenuItem(viewMenu, MenuButtons.GOTO);
        menuItem.addActionListener(actionEvent -> {
            //FIX 24 Casting PAGENR to Object makes it redundant
            String pageNumberStr = JOptionPane.showInputDialog(MenuButtons.PAGENR);
            int pageNumber = Integer.parseInt(pageNumberStr);
            //FIX 81 don't allow negative numbers and go back to first slide
            if (pageNumber < 0) {
                pageNumber = 1;
            }

            if (pageNumber > slideViewerComponent.getPresentation().getSize()) {
                pageNumber = slideViewerComponent.getPresentation().getSize();
                slideViewerComponent.setSlideNumber(slideViewerComponent.getPresentation().getSize() - 1);
            }
            slideViewerComponent.setSlideNumber(pageNumber - 1);
        });
        add(viewMenu);
    }

    private void help() {
        menuItem = getMenuItem(helpMenu, MenuButtons.ABOUT);
        menuItem.addActionListener(actionEvent -> AboutBox.show(parentF));
    }

    private MenuItem getMenuItem(Menu fileMenu, String open) {
        MenuItem menuItem;
        menuItem = getItem(fileMenu, open);
        return menuItem;
    }

    private MenuItem getItem(Menu fileMenu, String open) {
        MenuItem menuItem;
        fileMenu.add(menuItem = mkMenuItem(open));
        return menuItem;
    }

    //Creating a menu-item
    public MenuItem mkMenuItem(String name) {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
}
