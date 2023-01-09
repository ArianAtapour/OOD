import java.io.IOException;

/**
 * <p>An Accessor makes it possible to read and write data
 * for a presentation.</p>
 * <p>Non-abstract subclasses should implement the load and save methods.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

//FIX 53 Converting the accessor class into an interface
public interface Accessor {
	//FIX 4 - DEAD CODE DEMO_NAME AND DEFAULT_EXTENSION (NEVER USED)
	static Accessor getDemoAccessor() {
		return new DemoPresentation();
	}

	//FIX 52 Abstract classes should not have public constructors but protected ones INVALID

	//Fix 54 Reordering modifiers to comply with the Java Language Specifications INVALID
	void loadFile(Presentation p, String fn) throws IOException;

	void saveFile(Presentation p, String fn) throws IOException;

}
