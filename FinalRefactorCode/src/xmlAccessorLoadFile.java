//FIX 79 Removed Vector library since we don't use it anymore
import java.io.File;
import java.io.IOException;

        import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;


/**
 * XMLAccessor, reads and writes XML files
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class xmlAccessorLoadFile implements AccessorLoadFile {

    //FIX 9 -  DEAD CODE UNUSED PARAMETER DEFAULT_API_TO_USE
    //FIX 85 - Extracted XMLTags into a separate class

    private String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.item(0).getTextContent();

    }

    public void loadFile(Presentation presentation, String filename) throws IOException {
        //FIX 44 max initialiser to 0 is redundant
        //FIX 45 maxItems initialiser to 0 is redundant
        int slideNumber, itemNumber, max, maxItems;
        try {
            //FIX 80 To disable XXE attacks but still allow DOCTYPE declarations prohibited external enttities declarations, also had to rework the document part by adding DocumentBuilderFactory
            DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();
            builder.setFeature("http://xml.org/sax/features/external-general-entities", false);
            builder.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            DocumentBuilder builderDoc = builder.newDocumentBuilder();
            Document document = builderDoc.parse(new File(filename)); //Create a JDOM document
            Element doc = document.getDocumentElement();
            presentation.setTitle(getTitle(doc, XMLTags.SHOWTITLE));

            NodeList slides = doc.getElementsByTagName(XMLTags.SLIDE);
            max = slides.getLength();
            for (slideNumber = 0; slideNumber < max; slideNumber++) {
                Element xmlSlide = (Element) slides.item(slideNumber);
                Slide slide = new Slide();
                slide.setTitle(getTitle(xmlSlide, XMLTags.SLIDETITLE));
                presentation.append(slide);

                NodeList slideItems = xmlSlide.getElementsByTagName(XMLTags.ITEM);
                maxItems = slideItems.getLength();
                for (itemNumber = 0; itemNumber < maxItems; itemNumber++) {
                    Element item = (Element) slideItems.item(itemNumber);
                    loadSlideItem(slide, item);
                }
            }
        } catch (IOException iox) {
            //FIX 46 Unnecessary .toString()
            //FIX 47 Changing System.err.println to .printStackTrace for more error information
            //FIX 48 Collapsed ParserConfigurationException sax into SAXException for the similarity and lines of code saved
            iox.printStackTrace();
        } catch (SAXException | ParserConfigurationException sax) {
            sax.printStackTrace();
        }
    }

    protected void loadSlideItem(Slide slide, Element item) {
        int level = 1; // default
        NamedNodeMap attributes = item.getAttributes();
        String leveltext = attributes.getNamedItem(XMLTags.LEVEL).getTextContent();
        if (leveltext != null) {
            try {
                level = Integer.parseInt(leveltext);
            } catch (NumberFormatException x) {
                System.err.println(XMLTags.NFE);
            }
        }
        String type = attributes.getNamedItem(XMLTags.KIND).getTextContent();
        if (XMLTags.TEXT.equals(type)) {
            slide.append(new TextItem(level, item.getTextContent()));
        } else {
            if (XMLTags.IMAGE.equals(type)) {
                slide.append(new BitmapItem(level, item.getTextContent()));
            } else {
                System.err.println(XMLTags.UNKNOWNTYPE);
            }
        }
    }

    /*
    public void saveFile(Presentation presentation, String filename) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(filename));
        out.println("<?xml version=\"1.0\"?>");
        out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
        out.println("<presentation>");
        out.print("<showtitle>");
        out.print(presentation.getTitle());
        out.println("</showtitle>");
        for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
            Slide slide = presentation.getSlide(slideNumber);
            out.println("<slide>");
            out.println("<title>" + slide.getTitle() + "</title>");
            ArrayList<SlideItem> slideItems = slide.getSlideItems();
            for (SlideItem slideItem : slideItems) {
                //FIX 50 Casting slideItems.elementAt(itemNumber) to SlideItem is redundant so remove the casting
                out.print("<item kind=");
                if (slideItem instanceof TextItem) {
                    out.print("\"text\" level=\"" + slideItem.getLevel() + "\">");
                    out.print(((TextItem) slideItem).getText());
                } else {
                    if (slideItem instanceof BitmapItem) {
                        out.print("\"image\" level=\"" + slideItem.getLevel() + "\">");
                        out.print(((BitmapItem) slideItem).getName());
                    } else {
                        System.out.println("Ignoring " + slideItem);
                    }
                }
                out.println("</item>");
            }
            out.println("</slide>");
        }
        out.println("</presentation>");
        out.close();
    }
    */

}
