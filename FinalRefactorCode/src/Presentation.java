import java.util.ArrayList;


/**
 * <p>Presentations keeps track of the slides in a presentation.</p>
 * <p>Only one instance of this class is available.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Presentation {
    private String showTitle; //The title of the presentation
    private ArrayList<Slide> showList = new ArrayList<>(); //An ArrayList with slides
    private int currentSlideNumber = 0; //The number of the current slide

    //FIX 25 slideViewComponent initialiser null is redundant, so we removed the null
    //private SlideViewerComponent slideViewComponent; //The view component of the slides

    public Presentation() {
        /*
        slideViewComponent = null;
        clear();
        */
    }

    /*public Presentation(SlideViewerComponent slideViewerComponent) {
        this.slideViewComponent = slideViewerComponent;
        clear();
    }*/

    public int getSize() {
        return showList.size();
    }

    public String getTitle() {
        return showTitle;
    }

    public void setTitle(String nt) {
        showTitle = nt;
    }

    /*public void setShowView(SlideViewerComponent slideViewerComponent) {
        this.slideViewComponent = slideViewerComponent;
    }*/

    //Returns the number of the current slide
    public int getSlideNumber() {
        return currentSlideNumber;
    }

    public void setCurrentSlideNumber(int currentSlideNumber) {
        this.currentSlideNumber = currentSlideNumber;
    }
    //Change the current slide number and report it the the window
    /*
    public void setSlideNumber(int number) {
        currentSlideNumber = number;
        if (slideViewComponent != null) {
            slideViewComponent.update(this, getCurrentSlide());
        }
    }

    //Navigate to the previous slide unless we are at the first slide
    public void prevSlide() {
        if (currentSlideNumber > 0) {
            setSlideNumber(currentSlideNumber - 1);
        }
    }

    //Navigate to the next slide unless we are at the last slide
    public void nextSlide() {
        if (currentSlideNumber < (showList.size() - 1)) {
            setSlideNumber(currentSlideNumber + 1);
        }
    }

    //Remove the presentation
    void clear() {
        //FIX 25 Slide argument from ArrayList can be replaced with <> because of less verbose
        showList = new ArrayList<>();
        setSlideNumber(-1);
    }
    */

    //Add a slide to the presentation
    public void append(Slide slide) {
        showList.add(slide);
    }

    //Return a slide with a specific number
    public Slide getSlide(int number) {
        if (number < 0 || number >= getSize()) {
            return null;
        }
        //FIX 26 Casting showList.get() to Slide is redundant so remove the casting
        return showList.get(number);
    }

    public ArrayList<Slide> getShowList() {
        return showList;
    }

    //Return the current slide
    public Slide getCurrentSlide() {
        return getSlide(currentSlideNumber);
    }

    public void exit(int n) {
        System.exit(n);
    }
}
