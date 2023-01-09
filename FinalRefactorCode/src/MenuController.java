import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
//FIX 25 Removing unused import statements ActionListener and ActionEvent
import java.io.IOException;
import java.io.Serial;

import javax.swing.JOptionPane;

/** <p>The controller for the menu</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {

	//FIX 13 Making parentF variable final
	//FIX 58 changing parent name to something else (parentF) since it is used a field with the same name in MenuController
	private final Frame parentF; //The frame, only used as parentF for the Dialogs
	//FIX 14 Making presentation variable final

	//FIX 59 Making presentation variable transient
	private final transient Presentation presentation; //Commands are given to the presentation

	//FIX 15 Using @Serial annotation for serialVersionUID to give compile-time assurance
	@Serial
	private static final long serialVersionUID = 227L;
	
	protected static final String ABOUT = "About";
	protected static final String FILE = "File";
	protected static final String EXIT = "Exit";
	protected static final String GOTO = "Go to";
	protected static final String HELP = "Help";
	protected static final String NEW = "New";
	protected static final String NEXT = "Next";
	protected static final String OPEN = "Open";
	protected static final String PAGENR = "Page number?";
	protected static final String PREV = "Prev";
	protected static final String SAVE = "Save";
	protected static final String VIEW = "View";
	
	protected static final String TESTFILE = "testPresentation.xml";
	protected static final String SAVEFILE = "savedPresentation.xml";
	
	protected static final String IOEX = "IO Exception: ";
	protected static final String LOADERR = "Load Error";
	protected static final String SAVEERR = "Save Error";

	public MenuController(Frame frame, Presentation pres) {
		parentF = frame;
		presentation = pres;
		MenuItem menuItem;
		Menu fileMenu = new Menu(FILE);
		//FIX 60-65 Extracted the assignment out of the expression (filemMenu.add)
		menuItem = getMenuItem(fileMenu, OPEN);
		//FIX 16 - 23 Used lambda expression for ActionListener to enable functional programming and improve functionality
		menuItem.addActionListener(actionEvent -> {
			presentation.clear();
			Accessor xmlAccessor = new XMLAccessor();
			try {
				xmlAccessor.loadFile(presentation, TESTFILE);
				presentation.setSlideNumber(0);
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(parentF, IOEX + exc,
				 LOADERR, JOptionPane.ERROR_MESSAGE);
			}
			parentF.repaint();
		});
		menuItem = getMenuItem(fileMenu, NEW);
		menuItem.addActionListener(actionEvent -> {
			presentation.clear();
			parentF.repaint();
		});
		menuItem = getMenuItem(fileMenu, SAVE);
		menuItem.addActionListener(e -> {
			Accessor xmlAccessor = new XMLAccessor();
			try {
				xmlAccessor.saveFile(presentation, SAVEFILE);
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(parentF, IOEX + exc,
						SAVEERR, JOptionPane.ERROR_MESSAGE);
			}
		});
		fileMenu.addSeparator();
		menuItem = getMenuItem(fileMenu, EXIT);
		menuItem.addActionListener(actionEvent -> presentation.exit(0));
		add(fileMenu);
		Menu viewMenu = new Menu(VIEW);
		menuItem = getMenuItem(viewMenu, NEXT);
		menuItem.addActionListener(actionEvent -> presentation.nextSlide());
		menuItem = getMenuItem(viewMenu, PREV);
		menuItem.addActionListener(actionEvent -> presentation.prevSlide());

		//FIX: GO TO > LIMIT ON SLIDE COUNTER DONE!
		menuItem = getMenuItem(viewMenu, GOTO);
		menuItem.addActionListener(actionEvent -> {
			//FIX 24 Casting PAGENR to Object makes it redundant
			String pageNumberStr = JOptionPane.showInputDialog(PAGENR);
			int pageNumber = Integer.parseInt(pageNumberStr);
			//FIX 81 don't allow negative numbers and go back to first slide
			if(pageNumber < 0){
				pageNumber = 1;
			}

			if(pageNumber > presentation.getSize()){
				pageNumber = presentation.getSize();
				presentation.setSlideNumber(presentation.getSize() - 1);
			}
			presentation.setSlideNumber(pageNumber - 1);
		});
		add(viewMenu);
		Menu helpMenu = new Menu(HELP);
		menuItem = getMenuItem(helpMenu, ABOUT);
		menuItem.addActionListener(actionEvent -> AboutBox.show(parentF));
		setHelpMenu(helpMenu);		//Needed for portability (Motif, etc.).
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
