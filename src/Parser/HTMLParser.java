package Parser;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A Program to Parse HTML, it displays the document title,
 * provides meta information and keywords and lists the links 
 * from a Html Document provided by the crawler and diplays the
 * words occouring more than twice having length greater than 3.
 */

public class HTMLParser {
	public static String FrequentWords, Links;
	/** A function to display the document title, needs Document object as an argument*/
	public void ShowTitle(Document doc){
		String title = doc.title();
		String text = doc.body().text();
		System.out.println("title : " + title);
//		System.out.println("body : " + doc.body());
	}
	/** A function to display the meta, needs Document object as an argument*/
	public void GetMeta(Document doc){
		try{
		String description = doc.select("meta[name=description]").get(0).attr("content");
		System.out.println("Meta description : " + description);
	 
		//get meta keyword content
		String keywords = doc.select("meta[name=keywords]").first().attr("content");
		System.out.println("Meta keyword : " + keywords);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void GetContent(Document doc){
		Elements links = doc.select("h1");
		print("\nHeaders: (%d)", links.size());
		for (Element link : links) {
			Links += link.text();
			// get the value from href attribute
			System.out.println(link.text());
		}

	for( Element element : doc.select("p") )
	{
		FrequentWords+=element.text();
	    System.out.println(element.text());
	    // eg. you can use a StringBuilder and append lines here ...
	}
	WordOccurance(FrequentWords);
		
	}
	/**A Function to fetch all the word having occurance greater than specified Number, needs a string*/
	public void WordOccurance(String sentence){
		String[] myStringArray = sentence.split("\\s"); //Split the sentence by space.

        Map <String, Integer> wordOccurrences = new HashMap <String, Integer> (myStringArray.length);

        for (String word : myStringArray)
        if(word.length()>3)
        {
        	if (wordOccurrences.containsKey(word))
                wordOccurrences.put(word, wordOccurrences.get(word) + 1);
            else wordOccurrences.put(word, 1);
        }
        System.out.println("\nWords Occouring more than Twice having length greater than 3");

        for (String word : wordOccurrences.keySet())
            if (wordOccurrences.get(word) > 2)
                System.out.print(" " + word + " ");
    
	}
	/** A function to display the document links, needs Document object as an argument*/
	public void FetchLinks(Document doc) throws IOException{
		System.out.print("Fetching"+doc.id()+" ...");

		//        Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("a[href]");
		print("\nLinks: (%d)", links.size());
		for (Element link : links) {

			// get the value from href attribute
			System.out.println("\nlink : " + link.attr("href"));
			System.out.println("text : " + link.text());
		}

	}
	/** A function to display formated text, needs Strings as an argument*/
	private static void print(String msg, Object... args) {

		System.out.println(String.format(msg, args));
	}

	public void getWords(String sentence){
		String[] myStringArray = sentence.split("\\s"); //Split the sentence by space.

        Map <String, Integer> wordOccurrences = new HashMap <String, Integer> (myStringArray.length);

        for (String word : myStringArray)
        if(word.length()>3)
        {
        	if (wordOccurrences.containsKey(word))
                wordOccurrences.put(word, wordOccurrences.get(word) + 1);
            else wordOccurrences.put(word, 1);
        }

        for (String word : wordOccurrences.keySet())
            if (wordOccurrences.get(word) > 2)
                System.out.println("1b. - Tokens length>3 more than thrice: " + word + "\n");
    
	}
	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width-1) + ".";
		else
			return s;
	}

	public static void main(String[] args) throws IOException {
		Validate.isTrue(args.length == 1, "usage: supply url to fetch");
		String url = args[0];
		//        System.out.print("Fetching"+url+" ...");
		HTMLParser getLinks= new HTMLParser();
		File input = new File("input.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
//		Document doc = Jsoup.connect("http://en.wikipedia.org/wiki/Monte_Carlo_algorithm").get();
		getLinks.ShowTitle(doc);
		getLinks.GetMeta(doc);
		getLinks.FetchLinks(doc);
		getLinks.GetContent(doc);

	}
}