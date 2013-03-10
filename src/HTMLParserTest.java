import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.tags.MetaTag;

public class HTMLParserTest {
    public static void main(String args[]) {
        Parser parser = new Parser();
        //<meta name="description" content="Some texte about the site." />
        HasAttributeFilter filter = new HasAttributeFilter("name", "description");
        try {
            parser.setResource("http://www.ua.ac.be");
            NodeList list = parser.parse(filter);
            Node node = list.elementAt(0);

            if (node instanceof MetaTag) {
                MetaTag meta = (MetaTag) node;
                String description = meta.getAttribute("content");

                System.out.println(description);
                // Prints: "YouTube is a place to discover, watch, upload and share videos."
            }

        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

}