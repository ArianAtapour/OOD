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

	public MenuController(Frame frame, Presentation pres) {
		parentF = frame;
		presentation = pres;
		MenuItem menuItem;
		Menu fileMenu = new Menu(MenuButtons.FILE);
		//FIX 60-65 Extracted the assignment out of the expression (filemMenu.add)
		menuItem = getMenuItem(fileMenu, MenuButtons.OPEN);
		//FIX 16 - 23 Used lambda expression for ActionListener to enable functional programming and improve functionality
		menuItem.addActionListener(actionEvent -> {
			presentation.clear();
			Accessor xmlAccessor = new XMLAccessor();
			try {
				xmlAccessor.loadFile(presentation, MenuButtons.TESTFILE);
				presentation.setSlideNumber(0);
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(parentF, MenuButtons.IOEX + exc,
						MenuButtons.LOADERR, JOptionPane.ERROR_MESSAGE);
			}
			parentF.repaint();
		});
		menuItem = getMenuItem(fileMenu, MenuButtons.NEW);
		menuItem.addActionListener(actionEvent -> {
			presentation.clear();
			parentF.repaint();
		});
		menuItem = getMenuItem(fileMenu, MenuButtons.SAVE);
		menuItem.addActionListener(e -> {
			Accessor xmlAccessor = new XMLAccessor();
			try {
				xmlAccessor.saveFile(presentation, MenuButtons.SAVEFILE);
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(parentF, MenuButtons.IOEX + exc,
						MenuButtons.SAVEERR, JOptionPane.ERROR_MESSAGE);
			}
		});
		fileMenu.addSeparator();
		menuItem = getMenuItem(fileMenu, MenuButtons.EXIT);
		menuItem.addActionListener(actionEvent -> presentation.exit(0));
		add(fileMenu);
		Menu viewMenu = new Menu(MenuButtons.VIEW);
		menuItem = getMenuItem(viewMenu, MenuButtons.NEXT);
		menuItem.addActionListener(actionEvent -> presentation.nextSlide());
		menuItem = getMenuItem(viewMenu, MenuButtons.PREV);
		menuItem.addActionListener(actionEvent -> presentation.prevSlide());

		//FIX: GO TO > LIMIT ON SLIDE COUNTER DONE!
		menuItem = getMenuItem(viewMenu, MenuButtons.GOTO);
		menuItem.addActionListener(actionEvent -> {
			//FIX 24 Casting PAGENR to Object makes it redundant
			String pageNumberStr = JOptionPane.showInputDialog(MenuButtons.PAGENR);
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
		Menu helpMenu = new Menu(MenuButtons.HELP);
		menuItem = getMenuItem(helpMenu, MenuButtons.ABOUT);
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
